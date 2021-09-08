package ru;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.ApplicationPath;

@SpringBootApplication
@EnableProcessApplication

public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

}