package fr.diginamic.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean //INDISPENSABLE pour injecter un SecurityFilterChain dasn le conteneur IOC
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1) Active HTTP Basic pour les requêtes protégéeshttp
        http.httpBasic(Customizer.withDefaults());

        // 2) Toute requête doit être authentifiée
        http.authorizeHttpRequests(auth -> auth

        // 2a) Toutes les requêtes HTTP GET sont accessibles sans authentification.
           .requestMatchers(HttpMethod.GET, "/villes").permitAll()

        // 2b) Toute autre requête (POST, PUT, DELETE...) nécessite une authentification
           .anyRequest().authenticated()
        );

        return http.build();
    }
}

