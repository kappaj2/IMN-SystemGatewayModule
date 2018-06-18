package za.co.tman.systemgw.messaging.googlepubsub;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import za.co.tman.systemgw.messaging.InterModulePubSubMessage;


public interface GoogleChannelManager {

    void pubSubMessageSender(InterModulePubSubMessage interModulePubSubMessage);
    MessageChannel pubsubInputChannel();
    MessageHandler messageReceiver();

}
