package za.co.tman.systemgw.messaging.googlepubsub.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.retrying.RetrySettings;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import za.co.tman.systemgw.config.PubSubMessagingProperties;
import za.co.tman.systemgw.enums.PubSubMessageType;
import za.co.tman.systemgw.messaging.IMMessageProcessor;
import za.co.tman.systemgw.messaging.InterModulePubSubMessage;
import za.co.tman.systemgw.messaging.MessageImplementationCondition;
import za.co.tman.systemgw.messaging.googlepubsub.GooglePubSubHandler;


@Component
@Conditional(MessageImplementationCondition.class)
public class GooglePubSubHandlerImpl implements GooglePubSubHandler {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private String applicationModuleName;
    
    private PubSubTemplate pubSubTemplate;
    private IMMessageProcessor imMessageProcessor;
    private ObjectMapper objectMapper;
    private PubSubMessagingProperties pubSubMessagingProperties;
    private Map<PubSubMessageType, List<Publisher>> messageSubscriptionMap = new ConcurrentHashMap<>();
    
    public GooglePubSubHandlerImpl(PubSubTemplate pubSubTemplate,
                                   IMMessageProcessor imMessageProcessor,
                                   ObjectMapper objectMapper,
                                   PubSubMessagingProperties pubSubMessagingProperties,
                                   @Value("${spring.application.name}") String appName) {
        this.pubSubTemplate = pubSubTemplate;
        this.imMessageProcessor = imMessageProcessor;
        this.objectMapper = objectMapper;
        this.pubSubMessagingProperties = pubSubMessagingProperties;
        this.applicationModuleName = appName;
    }
    
    @PostConstruct
    public void subscriberToTopics() {
        List<String> subscriptionNamesList = getSubscriptionsForModule();
        subscriptionNamesList.stream().forEach(this::subscribeToSubscription);
    }
    
    @Override
    public void subscribeToSubscription(String subscriptionName) {
        
        log.info("Creating subscription for : ".concat(subscriptionName));
        
        pubSubTemplate.subscribe(subscriptionName, (pubsubMessage, ackReplyConsumer) -> {
            
            try {
                String messageData = pubsubMessage.getData().toStringUtf8();
                Map<String, String> attributesMap = pubsubMessage.getAttributesMap();
                String messageTypeCode = attributesMap.get("MessageTypeCode");
                
                if(messageTypeCode != null) {
                    log.info("Received message type : %s ".concat(messageTypeCode));
                }else{
                    log.error("Did not receive a messageType in the header - please investigate");
                }
                
                PubSubMessageType pubSubMessageType = PubSubMessageType.findPubSubMessageType(messageTypeCode);
                
                if (pubSubMessageType == null || pubSubMessageType.getMessageTypeCode().equals("String")) {
                    
                    log.info("Received normal string message : ".concat(messageData));
                    
                } else {
                    
                    InterModulePubSubMessage interModulePubSubMessage = objectMapper
                        .readValue(messageData, InterModulePubSubMessage.class);
                    interModulePubSubMessage.setMessageHeaders(attributesMap);
                    interModulePubSubMessage.setMessageId(pubsubMessage.getMessageId());
                    
                    imMessageProcessor.processMessageReceived(interModulePubSubMessage);
                    
                    publishMessage(interModulePubSubMessage);
                }
                
            } catch (Exception ex) {
                log.error("Error decoding received message", ex);
            }
            ackReplyConsumer.ack();
        });
    }
    
    /**
     * Publish a message - topic will be determined by the pubSubMessageType which is part of the message.
     * @param interModulePubSubMessage
     */
    @Override
    public void publishMessage(InterModulePubSubMessage interModulePubSubMessage) {
        
        try {
            String payloadJson = objectMapper.writeValueAsString(interModulePubSubMessage);
            PubSubMessageType mesType = interModulePubSubMessage.getPubSubMessageType();
            List<Publisher> publisherList = getTargetPublishers(mesType);
            
            PubsubMessage pubsubMessage =
                PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(payloadJson))
                    .putAttributes("MessageType",
                        interModulePubSubMessage.getPubSubMessageType().getMessageTypeCode())
                    .build();
            
            for (Publisher publisher : publisherList) {
                publisher.publish(pubsubMessage);
            }
            
        } catch (IOException io) {
            log.error("Error processing submit : " + io.getLocalizedMessage(), io);
        }
    }
    
    /**
     * Retrieve the list of topics the message must be send to.
     *
     * @param pubSubMessageType
     * @return List<String> A list of topic names. If none found then the list will be empty.
     */
    private List<Publisher> getTargetPublishers(PubSubMessageType pubSubMessageType) {
        
        if (messageSubscriptionMap.containsKey(pubSubMessageType)) {
            
            return messageSubscriptionMap.get(pubSubMessageType);
            
        } else {
            
            try {
                org.threeten.bp.Duration retryDelay = org.threeten.bp.Duration.ofSeconds(1l);
                double retryDelayMultiplier = 2.0;
                org.threeten.bp.Duration maxRetryDelay = org.threeten.bp.Duration.ofSeconds(50);
                
                RetrySettings retrySettings = RetrySettings.newBuilder().
                    setInitialRetryDelay(retryDelay).
                    setMaxRetryDelay(maxRetryDelay).
                    setRetryDelayMultiplier(retryDelayMultiplier).
                    build();
                
                List<Publisher> publisherList = new ArrayList<>();
                
                for (String topic : getTargetTopicNames(pubSubMessageType)) {
                    Publisher publisher = Publisher.newBuilder(topic).
                        setRetrySettings(retrySettings).build();
                    
                    publisherList.add(publisher);
                }
                
                messageSubscriptionMap.put(pubSubMessageType, publisherList);
                return publisherList;
                
            } catch (IOException ioe) {
                log.error("Error building publishers. " + ioe.getLocalizedMessage(), ioe);
            }
        }
        return new ArrayList<>();
    }
    
    /**
     * Retrieve the list of topics the message must be send to from this Module
     * These are defined as follows in the application.yml file:
     * <p>
     * pubsubmessagetype:
     * modules:
     * subscriptions:
     * -
     * application_module_name: BillingModule
     * topic_name: BillingTopic
     * subscription_name: BillingTopicSub
     *
     * @param pubSubMessageType
     * @return List<String> A list of topic names. If none found then the list will be empty.
     */
    @Override
    public List<String> getTargetTopicNames(PubSubMessageType pubSubMessageType) {
        
        List<PubSubMessagingProperties.Modules> modules
            = pubSubMessagingProperties.getModules().stream().filter(module ->
            (module.getApplicationModuleName().equals(applicationModuleName)
                && module.getPubSubMessageType().equalsIgnoreCase(pubSubMessageType.getMessageTypeCode())))
            .collect(Collectors.toList());
        
        if (!modules.isEmpty()) {
            return modules.get(0).getTopicsList();
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * Get the list of subscription names that must be subscribed to for this module.
     *
     * @return
     */
    @Override
    public List<String> getSubscriptionsForModule() {
        List<PubSubMessagingProperties.Subscriptions> subscriptionsList = pubSubMessagingProperties.getSubscriptions()
            .stream().filter(module ->
                module.getApplicationModuleName().equals(applicationModuleName)
            ).collect(Collectors.toList());
        
        List<String> subscriptionNameList = new ArrayList<>();
        subscriptionsList.stream().forEach(sub -> subscriptionNameList.add(sub.getSubscriptionName()));
        
        return subscriptionNameList;
    }
}
