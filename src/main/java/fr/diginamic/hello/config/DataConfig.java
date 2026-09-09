package fr.diginamic.hello.config;

import fr.diginamic.hello.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataConfig implements CommandLineRunner {

    @Value("${application.init}")
    private boolean initData;

    @Autowired
    private JpaUserDetailsService service;

    @Override
    public void run(String... args) throws Exception {

    }
}