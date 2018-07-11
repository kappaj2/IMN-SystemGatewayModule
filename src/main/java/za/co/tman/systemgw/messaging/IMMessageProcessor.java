package za.co.tman.systemgw.messaging;

public interface IMMessageProcessor {
    void processMessageReceived(InterModulePubSubMessage interModulePubSubMessage);
}
