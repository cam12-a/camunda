package ru;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.Services.CallExternalService;

@SpringBootApplication
@EnableProcessApplication
public class Application {

  public static void main(String... args) {

    SpringApplication.run(Application.class, args);

  }

}