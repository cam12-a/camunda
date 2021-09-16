package ru.Listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.Model.ApplicationData;
import ru.Services.CreateAppInstance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class kafkaConsumer {

    @Autowired
    ApplicationData applicationData;

    @Autowired
    CreateAppInstance createAppInstance;


   @KafkaListener(topics = "recruitment_app_json", groupId = "group_json",containerFactory = "applicationKafkaListenerFactory")
    public void consume(ApplicationData appData) throws ParseException {
        System.out.println("Consume message "+appData);
        Map<String, Object> variables=new HashMap<>();

       DateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
       DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

        variables.put("applicationGUI",appData.getApplicationGUI());
        variables.put("firstName",appData.getFirstName());
        variables.put("name",appData.getName());
        variables.put("lastName",appData.getLastName());
        variables.put("dateBirth",formatter1.format(formatter.parse(appData.getDateBirth().toString())));
        variables.put("stage",appData.getStage());
        variables.put("status",appData.getStatus());
        variables.put("jobPlace",appData.getJobPlace());

        createAppInstance.appInstance(variables);


    }
}
