package za.co.tman.systemgw.security;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;


public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    
    private static final String REQUEST_HEADER_NAME = "Authorization";
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws
                                                                                                                IOException {
    
        String jwtTokenStored = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        
        request.getHeaders().add(REQUEST_HEADER_NAME, "Bearer "+jwtTokenStored);
    
        return execution.execute(request, body);
    }
}
