package za.co.ajk.systemgateway.web.rest.google;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.systemgateway.messaging.InterModulePubSubMessage;
import za.co.ajk.systemgateway.messaging.MessageSender;


@RestController
@RequestMapping("/api/test/v1")
public class GoogleTester {

    private MessageSender messageSender;

    public GoogleTester(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @GetMapping("/send-generic")
    @Timed
    @ResponseBody
    public void sendGenericMessage() throws Exception {
        messageSender.sendGenericMessage();
    }
    
    @GetMapping("/send-incident")
    @Timed
    @ResponseBody
    public void sendIncidentMessage() throws Exception {
        messageSender.sendIncidentTestMessage();
    }
    
    @PostMapping("/send-obj")
    @Timed
    @ResponseBody
    public void sendObjMessage(@RequestBody InterModulePubSubMessage interModulePubSubMessage) {
        messageSender.sendObjMessage(interModulePubSubMessage);
    }

}
