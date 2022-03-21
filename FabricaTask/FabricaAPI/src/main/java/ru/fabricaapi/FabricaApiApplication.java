package ru.fabricaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class FabricaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FabricaApiApplication.class, args);
    }

}
