package ru.services;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;

import org.springframework.stereotype.Service;


@Service("MyResAPI")
public class MyResAPI {

    public void normaStart(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String id=runtimeService.startProcessInstanceByKey("MyProccessExample").getId();
        runtimeService.setVariable(id,"source","api");

    }


}
