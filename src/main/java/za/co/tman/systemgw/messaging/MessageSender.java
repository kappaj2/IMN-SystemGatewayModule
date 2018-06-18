package za.co.tman.systemgw.messaging;

public interface MessageSender {

    void sendIncidentTestMessage() throws Exception;
    
    void sendGenericMessage() throws Exception;
    
    void sendObjMessage(InterModulePubSubMessage interModulePubSubMessage);
}
