package ru.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunProcess {

    @PostMapping(value = "/camunda/rest-api/{id}")
    public void GenerateProcess(int id)
    {

    }

}
