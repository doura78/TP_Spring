package fr.diginamic.hello.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de gestion des villes")
                        .version("1.0")
                        .description("Cette API permet de gérer des villes : consultation, création, modification, suppression et recherche.")
                        .contact(new Contact()
                                .name("Nom du contact")
                                .email("email@exemple.com").url("URL du contact"))
                );
    }
}