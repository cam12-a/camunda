package ru.Listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.Model.ApplicationData;
import ru.Services.CreateAppInstance;

import java.util.HashMap;
import java.util.Map;

@Service
public class kafkaConsumer {

    @Autowired
    ApplicationData applicationData;

    @Autowired
    CreateAppInstance createAppInstance;

    @KafkaListener(topics = "recruitment_app", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consume message "+message);
    }

   @KafkaListener(topics = "recruitment_app_json", groupId = "group_json",containerFactory = "applicationKafkaListenerFactory")
    public void consume(ApplicationData appData) {
        System.out.println("Consume message "+appData);
        Map<String, Object> variables=new HashMap<>();

        variables.put("applicationGUI",appData.getApplicationGUI());
        variables.put("firstName",appData.getFirstName());
        variables.put("name",appData.getName());
        variables.put("lastName",appData.getLastName());
        variables.put("dateBirth",appData.getDateBirth());
        variables.put("stage",appData.getStage());
        variables.put("status",appData.getStatus());
        variables.put("jobPlace",appData.getJobPlace());

        createAppInstance.appInstance(variables);

        applicationData.setApplicationGUI(appData.getApplicationGUI());
        applicationData.setStatus(appData.getStatus());
        applicationData.setStage(appData.getStage());
        applicationData.setJobPlace(appData.getJobPlace());
        applicationData.setLastName(appData.getLastName());
        applicationData.setFirstName(appData.getLastName());
        applicationData.setDateBirth(appData.getDateBirth());
        applicationData.setName(appData.getName());
    }
}
