package ru.Controllers;


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
        System.out.println(processExist.isProcessExist(appNumber));
        if(validateStatus(statusValue) && processExist.isProcessExist(appNumber)){
            applicationData.setStatus(statusValue);
            kafkaTemplate.send(TOPIC,applicationData.getStatus());
            closeApplication.closeApplication(appNumber);
            return "Статус заявки успешно изменено на "+ applicationData.getStatus();

        }
        else return "не правильная значения статуса или неверный номер заявки";
    }

    @GetMapping("/appData/{appNumber}/")
    public ApplicationData getAppData(@PathVariable  String appNumber)
    {
        closeApplication.closeApplication(appNumber);
        System.out.println("ok");
        //System.out.println(applicationData.toString());
        return applicationData;
    }

    public static boolean validateStatus(String status){
        if(status.equals("cancelled") || status.equals("approved"))
            return true;
        else
            return false;
    }


}
