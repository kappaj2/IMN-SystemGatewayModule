package za.co.tman.systemgw.messaging.googlepubsub.attempts;

import org.springframework.integration.annotation.MessagingGateway;


public class SomeOptionsTesting {
    
    //    @Bean
    //    @ServiceActivator(inputChannel = "logChannel")
    //    public LoggingHandler logging() {
    //        LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.DEBUG);
    //        adapter.setLoggerName("TEST_LOGGER");
    //        adapter.setLogExpressionString("headers.id + ': ' + payload");
    //        return adapter;
    //    }
    //
    //    @MessagingGateway(defaultRequestChannel = "logChannel")
    //    public interface MyGateway {
    //        void sendToLogger(String data);
    //
    //    }
    
    
    /**
     * This option can send with a MessagingGateway - uses SpringIntegration option.
     *
     */
    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    public interface PubsubOutboundGateway {
        void sendToPubsub(String text);
    }
}
