package lk.ijse.agms.api_gateway.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;





@Configuration
@EnableWebFluxSecurity // Required for Reactive Gateway [cite: 45]
public class SecurityConfig {

    @Value("${agms.jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // Permitting login/register routes so users can actually GET a token
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/users/register").permitAll()
                        // All greenhouse management routes (crops, zones, etc.) remain protected
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();


    }






    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);

        SecretKeySpec spec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(spec).build();
    }

}