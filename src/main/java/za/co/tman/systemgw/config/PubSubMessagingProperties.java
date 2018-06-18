package za.co.tman.systemgw.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Properties mapper class for the PubSub message sender.
 * This will determine to which topic(s) each meassage must be submitted to.
 * Each module handles each message type differently. The YML file config can now determine
 * how the messages gets distributed.
 */
@ConfigurationProperties(prefix = "pubsubmessagetype", ignoreUnknownFields = false)
@Component
public class PubSubMessagingProperties {
    
    private final List<Modules> modules = new ArrayList<>();
    private final TopicsRequired topicsRequired = new TopicsRequired();
    private final List<Subscriptions> subscriptions = new ArrayList<>();
    
    public List<Modules> getModules() {
        return modules;
    }
    
    public TopicsRequired getTopicsRequired(){
        return topicsRequired;
    }
    
    public List<Subscriptions> getSubscriptions(){
        return subscriptions;
    }
    
    public static class Modules {
        
        private String applicationModuleName;
        private String pubSubMessageType;
        private List<String> topicsList;
        
        public String getApplicationModuleName() {
            return applicationModuleName;
        }
        
        public void setApplicationModuleName(String applicationModuleName) {
            this.applicationModuleName = applicationModuleName;
        }
    
        public String getPubSubMessageType() {
            return pubSubMessageType;
        }
    
        public void setPubSubMessageType(String pubSubMessageType) {
            this.pubSubMessageType = pubSubMessageType;
        }
    
        public List<String> getTopicsList() {
            return topicsList;
        }
    
        public void setTopicsList(List<String> topicsList) {
            this.topicsList = topicsList;
        }
    }
    
    public static class TopicsRequired{
        private List<String> topicNames = new ArrayList<>();
    
        public void setTopicNames(List<String> topicNames) {
            this.topicNames = topicNames;
        }
    
        public List<String> getTopicNames() {
            return topicNames;
        }
    }
   
    public static class Subscriptions{
        private String applicationModuleName;
        private String topicName;
        private String subscriptionName;
    
        public String getApplicationModuleName() {
            return applicationModuleName;
        }
    
        public void setApplicationModuleName(String applicationModuleName) {
            this.applicationModuleName = applicationModuleName;
        }
    
        public String getTopicName() {
            return topicName;
        }
    
        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
    
        public String getSubscriptionName() {
            return subscriptionName;
        }
    
        public void setSubscriptionName(String subscriptionName) {
            this.subscriptionName = subscriptionName;
        }
    }
}
