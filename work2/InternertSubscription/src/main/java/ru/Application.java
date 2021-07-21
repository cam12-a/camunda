package ru;



import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

//@ComponentScan(basePackages={"ru.service","ru.request","ru.models"})
        public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

}