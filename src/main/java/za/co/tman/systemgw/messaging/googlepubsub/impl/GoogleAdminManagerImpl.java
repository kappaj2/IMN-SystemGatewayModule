package za.co.tman.systemgw.messaging.googlepubsub.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import za.co.tman.systemgw.config.PubSubMessagingProperties;
import za.co.tman.systemgw.messaging.MessageImplementationCondition;
import za.co.tman.systemgw.messaging.googlepubsub.GoogleAdminManager;


@Component
@Conditional(MessageImplementationCondition.class)
public class GoogleAdminManagerImpl implements GoogleAdminManager {
    
    private static Logger log = LoggerFactory.getLogger(GoogleAdminManagerImpl.class);
    
    private PubSubMessagingProperties pubSubMessagingProperties;
    private PubSubAdmin pubSubAdmin;
    
    @Autowired
    public GoogleAdminManagerImpl(PubSubMessagingProperties pubSubMessagingProperties,
                                  PubSubAdmin pubSubAdmin) {
        this.pubSubMessagingProperties = pubSubMessagingProperties;
        this.pubSubAdmin = pubSubAdmin;
    }
    
    @Override
    public void checkTopics() {
        
        List<String> topicNamesList = pubSubMessagingProperties.getTopicsRequired().getTopicNames();
        List<String> existingTopicList = getExistingTopics();
        
        topicNamesList.forEach(topicName -> {
            boolean exists = checkTopicExists(existingTopicList, topicName);
            if (!exists) {
                createTopic(topicName);
            }
        });
    }
    
    @Override
    public void checkSubscriptions() {
        List<PubSubMessagingProperties.Subscriptions> subscriptionsList = pubSubMessagingProperties.getSubscriptions();
        List<String> existingSubscriptionsList = getExistingSubscriptions();
        
        subscriptionsList.forEach(sub -> {
            boolean exists = checkSubscriptionExists(existingSubscriptionsList, sub.getSubscriptionName());
            log.info("Checking Topic : " + sub.getTopicName() + " for subscription : " + sub.getSubscriptionName()
                + " exists : " + exists);
            
            if (!exists) {
                createSubscription(sub.getTopicName(), sub.getSubscriptionName());
            }
        });
    }
    
    private boolean checkTopicExists(List<String> topicsList, String topicName) {
        return topicsList.stream().anyMatch(top -> top.equalsIgnoreCase(topicName));
    }
    
    private boolean checkSubscriptionExists(List<String> subscriptionList, String subscriptionName) {
        return subscriptionList.stream().anyMatch(sub -> sub.equalsIgnoreCase(subscriptionName));
    }
    
    private List<String> getExistingTopics() {
        
        return pubSubAdmin.listTopics()
            .stream()
            .map(topic -> extractTopicNameOnly(topic.getName()))
            .collect(Collectors.toList());
        
    }
    
    private List<String> getExistingSubscriptions() {
        return pubSubAdmin.listSubscriptions()
            .stream()
            .map(subscription -> extractTopicNameOnly(subscription.getName()))
            .collect(Collectors.toList());
    }
    
    private void createTopic(String topicName) {
        pubSubAdmin.createTopic(topicName);
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException ie) {
        
        }
    }
    
    private void createSubscription(String topicName, String subscriptionName) {
        pubSubAdmin.createSubscription(subscriptionName, topicName);
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException ie) {
        
        }
    }
    
    private String extractTopicNameOnly(String inputValue) {
        return inputValue.substring(inputValue.lastIndexOf("/") + 1);
        
    }
}
