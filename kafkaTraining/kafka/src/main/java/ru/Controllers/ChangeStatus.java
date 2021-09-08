package ru.Controlers;


import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Model.ApplicationData;
import ru.Services.CheckProcessExist;
import ru.Services.CloseApplication;

import java.util.List;

@RestController

public class ChangeStatus {

    @Autowired
    ApplicationData applicationData;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    CheckProcessExist processExist;

    @Autowired
    CloseApplication closeApplication;
    private static final String TOPIC="recruitment_app";
    @PostMapping("/status/{appNumber}/{statusValue}/")
    public String changeStatusUsingAPI(@PathVariable String appNumber,@PathVariable String statusValue)
    {
        System.out.println(processExist.isProcessExist(appNumber));
        if(validateStatus(statusValue) && processExist.isProcessExist(appNumber)){
            applicationData.setStatus(statusValue);
            kafkaTemplate.send(TOPIC,applicationData.getStatus());
            closeApplication.closeApplication(appNumber);
            return "Статус заявки успешно изменено на "+ applicationData.getStatus();
        }
        else return "не правильная значения статуса или неверный номер заявки";
    }

    public static boolean validateStatus(String status){
        if(status.equals("cancelled") || status.equals("approved"))
            return true;
        else
            return false;
    }
}
