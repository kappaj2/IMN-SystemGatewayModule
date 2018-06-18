package za.co.tman.systemgw.config.cmdline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import za.co.tman.systemgw.messaging.googlepubsub.GoogleAdminManager;


/**
 * This class will do the required configuration checks when the application starts up.
 * Currently it will check and configure the Google PubSub environment.
 */
@Component
public class ApplicationConfigChecker implements CommandLineRunner {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Value("${googlepubsub.enabled}")
    private boolean googlePubSubEnabled;
    
    @Autowired
    private GoogleAdminManager googleAdminManager;
//
    @Override
    public void run(String... strings) throws Exception {
        try {
            if (googlePubSubEnabled) {
                log.info("Running CommandLineRunner");
                googleAdminManager.checkTopics();
                log.info("Checked all the topics - now doing subscriptions");
        
                Thread.sleep(30000l);
                googleAdminManager.checkSubscriptions();
                log.info("$$$$$$$$$$    Completed PubSub verification   $$$$$$$$$$");
            } else {
                log.info("Google PubSub seems to be disabled");
            }
        }catch(Exception ex){
            log.error("Error starting up and checking Google env : "+ex.getLocalizedMessage(), ex);
        }
    }
}
