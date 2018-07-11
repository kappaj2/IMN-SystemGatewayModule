package za.co.tman.systemgw.messaging.googlepubsub;

import java.util.List;

import za.co.tman.systemgw.enums.PubSubMessageType;
import za.co.tman.systemgw.messaging.InterModulePubSubMessage;


public interface GooglePubSubHandler {

    void subscribeToSubscription(String subscriptionName);
    void publishMessage(InterModulePubSubMessage interModulePubSubMessage);
    List<String> getTargetTopicNames(PubSubMessageType pubSubMessageType);
    List<String> getSubscriptionsForModule();
}
