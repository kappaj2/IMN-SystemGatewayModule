package za.co.tman.systemgw.messaging.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.tman.systemgw.enums.EventType;
import za.co.tman.systemgw.enums.IncidentPriority;
import za.co.tman.systemgw.enums.PubSubMessageType;
import za.co.tman.systemgw.messaging.InterModulePubSubMessage;
import za.co.tman.systemgw.messaging.MessageImplementationCondition;
import za.co.tman.systemgw.messaging.MessageSender;
import za.co.tman.systemgw.messaging.googlepubsub.GooglePubSubHandler;


/**
 * Utility class that will send a message to the different topics configured for the module and message type.
 */
@Service
@Conditional(MessageImplementationCondition.class)
public class MessageSenderImpl implements MessageSender {
    
    private GooglePubSubHandler googlePubSubHandler;
    
    @Value("${spring.application.name}")
    private String moduleName;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public MessageSenderImpl(GooglePubSubHandler googlePubSubHandler) {
        this.googlePubSubHandler = googlePubSubHandler;
    }
    
    /**
     * This is a test message that will be submitted.
     *
     * @throws Exception
     */
    @Override
    public void sendIncidentTestMessage() throws Exception {
        
        InterModulePubSubMessage interModulePubSubMessage = new InterModulePubSubMessage();
        
        interModulePubSubMessage.setEventType(EventType.START_EVENT);
        interModulePubSubMessage
            .setIncidentDescription("Die ding het ontplof en die hele wereld aan die brand gesteek...");
        interModulePubSubMessage.setIncidentHeader("Die ding het ontplof");
        interModulePubSubMessage.setIncidentNumber(100L);
        interModulePubSubMessage.setIncidentPriority(IncidentPriority.CRITICAL);
        interModulePubSubMessage.setMessageDateCreated(Instant.now());
        interModulePubSubMessage.setOperatorName("Andre");
        interModulePubSubMessage.setOriginatingApplicationModuleName("TestModule");
        interModulePubSubMessage.setPubSubMessageType(PubSubMessageType.INCIDENT);
        
        googlePubSubHandler.publishMessage(interModulePubSubMessage);
    }
    
    @Override
    public void sendGenericMessage() throws Exception {
        
        InterModulePubSubMessage interModulePubSubMessage = new InterModulePubSubMessage();
        
        interModulePubSubMessage.setEventType(EventType.GENERIC_MESSAGE);
        interModulePubSubMessage
            .setIncidentDescription("Generic message to test communications");
        interModulePubSubMessage.setIncidentHeader("Gen Meesage");
        interModulePubSubMessage.setIncidentNumber(null);
        interModulePubSubMessage.setIncidentPriority(IncidentPriority.LOW);
        interModulePubSubMessage.setMessageDateCreated(Instant.now());
        interModulePubSubMessage.setOperatorName("Andre");
        interModulePubSubMessage.setOriginatingApplicationModuleName(moduleName);
        interModulePubSubMessage.setPubSubMessageType(PubSubMessageType.GENERIC);
        
        googlePubSubHandler.publishMessage(interModulePubSubMessage);
    }
    
    @Override
    public void sendObjMessage(InterModulePubSubMessage interModulePubSubMessage) {
        googlePubSubHandler.publishMessage(interModulePubSubMessage);
    }
}
