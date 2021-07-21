package ru.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



import ru.services.MyResAPI;

@RestController
public class camundaResAPI {

   @PostMapping(value = "/camunda/rest-api")
    public void startProcessDefinition()
    {
        MyResAPI myResAPI=new MyResAPI();
        myResAPI.normaStart();

    }
}
