package za.co.tman.systemgw.messaging.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import za.co.tman.systemgw.enums.EventType;
import za.co.tman.systemgw.enums.PubSubMessageType;
import za.co.tman.systemgw.messaging.IMMessageProcessor;
import za.co.tman.systemgw.messaging.InterModulePubSubMessage;


@Component(value = "imMessageProcessor")
public class IMMessageProcessorImpl implements IMMessageProcessor {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void processMessageReceived(InterModulePubSubMessage interModulePubSubMessage) {
        
        Map<String, String> headersMap = interModulePubSubMessage.getMessageHeaders();
        
        String messageId = interModulePubSubMessage.getMessageId();
        log.info("MessageId : " + messageId);
        
        if (headersMap.containsKey("PubSubMessageType")) {
            String mes = headersMap.get("PubSubMessageType");
        }
        
        PubSubMessageType messageType = PubSubMessageType.INCIDENT;
        
        switch (messageType) {
            case GENERIC:
                try {
                    log.info("Generic message received ...");
                    
                } catch (Exception ioe) {
                    log.error("Error parsing payload : ", ioe.getMessage());
                }
                break;
            case INCIDENT:
                try {
                    
                    EventType eventType = interModulePubSubMessage.getEventType();
                    log.info("Received eventType : " + eventType.toString());
                    
                } catch (Exception io) {
                    io.printStackTrace();
                }
                break;
            default:
                log.error("Unknonwn message type ");
        }
    }
}
