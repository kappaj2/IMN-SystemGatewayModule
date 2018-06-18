package za.co.tman.systemgw.messaging.googlepubsub.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;


/**
 * Utility class to handle common functionality...nice.
 * Show how to use channel interceptors.
 */
@Component(value="channelInterceptorAdapter")
public class CountingChannelInterceptor extends ChannelInterceptorAdapter {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final AtomicInteger sendCount = new AtomicInteger();
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        sendCount.incrementAndGet();
        log.info("Message count : "+sendCount.intValue());
        return message;
    }
}
