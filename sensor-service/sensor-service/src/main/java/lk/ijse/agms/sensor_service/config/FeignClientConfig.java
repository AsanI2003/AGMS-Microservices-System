package lk.ijse.agms.sensor_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                // Get the Authorization header from the incoming request
                String authHeader = attributes.getRequest().getHeader("Authorization");
                if (authHeader != null) {
                    // Propagate it to the outgoing Feign call (Zone Service)
                    requestTemplate.header("Authorization", authHeader);
                }
            }
        };
    }
}