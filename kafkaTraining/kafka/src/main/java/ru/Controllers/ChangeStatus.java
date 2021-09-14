package ru.Controllers;


import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Connector.SendStatus;
import ru.Model.ApplicationData;
import ru.Services.CheckProcessExist;
import ru.Services.CloseApplication;

import java.util.HashMap;
import java.util.Map;


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

    @Autowired
    SendStatus sendStatus;
    private static final String TOPIC="recruitment_app";
    @PostMapping("/status/{appNumber}/{statusValue}/")
    public String changeStatusUsingAPI(@PathVariable String appNumber,@PathVariable String statusValue)
    {
        System.out.println("status value"+statusValue);
        if(validateStatus(statusValue) && processExist.isProcessExist(appNumber)){

            closeApplication.setStatusValue(statusValue,appNumber);
            closeApplication.closeApplication(appNumber);
            applicationData.setStatus(statusValue);
            Map<String,Object> variable=new HashMap<String,Object>();
            variable.put("applicationGUI",applicationData.getApplicationGUI());
            variable.put("dateBirth",applicationData.getDateBirth());
            variable.put("firstName",applicationData.getFirstName());
            variable.put("lastName",applicationData.getLastName());
            variable.put("jobPlace",applicationData.getJobPlace());
            variable.put("stage",applicationData.getStage());
            variable.put("status",applicationData.getStatus());
            closeApplication.completeTask(appNumber,variable);



            return "Статус заявки успешно изменено на "+ applicationData.getStatus();

        }
        else return "не правильная значения статуса или неверный номер заявки";
    }

    @GetMapping("/appData/{appNumber}/")
    public ApplicationData getAppData(@PathVariable  String appNumber)
    {
        closeApplication.closeApplication(appNumber);
        System.out.println("ok");
        System.out.println("controller value data "+applicationData.toString());
        return applicationData;
    }

    public static boolean validateStatus(String status){
        if(status.equals("cancelled") || status.equals("approved"))
            return true;
        else
            return false;
    }


}
