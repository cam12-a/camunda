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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Connectors.GetStatusValue;
import ru.Model.ApplicationData;
import ru.Services.CheckProcessExist;
import ru.Services.CloseApplication;

import java.util.List;

@RestController

public class ChangeStatus {

    @Autowired
    ApplicationData applicationData;
    @Autowired
    KafkaTemplate<String,ApplicationData> kafkaTemplate;
    @Autowired
    CheckProcessExist processExist;

    @Autowired
    CloseApplication closeApplication;

    @Autowired
    GetStatusValue getStatusValue;
    private static final String TOPIC="recruitment_app_json";
    @PostMapping("/status/{appNumber}/{statusValue}/")
    public String changeStatusUsingAPI(@PathVariable String appNumber,@PathVariable String statusValue)
    {

        if(validateStatus(statusValue) && processExist.isProcessExist(appNumber)){
            applicationData.setApplicationGUI(appNumber);
            applicationData.setStatus(statusValue);
            //kafkaTemplate.send(TOPIC,applicationData.getStatus());
            closeApplication.closeApplication(appNumber);
           return "Статус заявки успешно изменено на "+ applicationData.getStatus();
        }
        else return "не правильная значения статуса или неверный номер заявки";
    }

    public static boolean validateStatus(String status){
        return status.equals("cancelled") || status.equals("approved");
    }


    @GetMapping("/appData/{appNumber}/")
    public ApplicationData getAppData(@PathVariable  String appNumber)
    {

        //closeApplication.closeApplication(appNumber);
        getStatusValue.sendStatus(appNumber);
        System.out.println( getStatusValue.sendStatus(appNumber).toString());
        return applicationData;
    }

}
