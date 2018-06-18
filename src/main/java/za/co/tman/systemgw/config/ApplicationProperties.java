package za.co.tman.systemgw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Systemgateway.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    
    public final RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
    
    public RestTemplateConfig getRestTemplateConfig() {
        return restTemplateConfig;
    }
    
    public static class RestTemplateConfig {
        
        private Integer connectTimeout;
        private Integer readTimeout;
        
        public Integer getConnectTimeout() {
            return connectTimeout;
        }
        
        public void setConnectTimeout(Integer connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
        
        public Integer getReadTimeout() {
            return readTimeout;
        }
        
        public void setReadTimeout(Integer readTimeout) {
            this.readTimeout = readTimeout;
        }
    }
    
}
