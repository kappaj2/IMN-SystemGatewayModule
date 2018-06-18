package za.co.tman.systemgw.web.rest;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/test/v1")
public class TestTemplateCalls {
    
        private RestTemplate restTemplate;

        public TestTemplateCalls(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }
        
        @RequestMapping(method = RequestMethod.GET, value = "/regions")
        public Collection<Object> names() {

            ParameterizedTypeReference<List<Object>> ptr =
                new ParameterizedTypeReference<List<Object>>() {
                };

            ResponseEntity<List<Object>> responseEntity =
                this.restTemplate.exchange("http://incidentmodule/api/regions",
                    HttpMethod.GET,
                    null,
                    ptr
                );

            List<Object> regions = responseEntity
                .getBody()
                .stream()
                .collect(Collectors.toList());


            //  Another option is to retrieve the payload as a JSON string, and then to parse/handle
            //  the contents here.
            ResponseEntity<String> respJson = restTemplate
                .getForEntity("http://incidentmodule/api/regions", String.class);

            return regions;

        }
}
