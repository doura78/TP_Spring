package fr.diginamic.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers(HttpMethod.GET, "/villes/**").hasAnyRole("USER", "ADMIN")
//                .requestMatchers(HttpMethod.POST, "/villes/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/villes/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/villes/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}