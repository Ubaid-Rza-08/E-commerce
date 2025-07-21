package com.ubaid.customer_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth->auth
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2->oauth2
                        .jwt(jwtConfigurer->jwtConfigurer
                                .decoder(jwtDecoder())));
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String base64Secret = "VYm92hHgq/FS0I4awR87SFnD7t68dZ0je8PI4Q45ZwhJex9VILCWdJhbfipCROpHZ8J7Z5CfoqWqPpgQ0Yo4CA==";
        byte[] decodedSecret = Base64.getDecoder().decode(base64Secret);
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(decodedSecret, "HmacSHA512"))
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

}

