package ru.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.processEngine.AuthorizationConfiguration;

@Configuration
public class ProjectConfiguration {

    @Bean
    public AuthorizationConfiguration authorizationConfiguration(){
        return new AuthorizationConfiguration();
    }
}