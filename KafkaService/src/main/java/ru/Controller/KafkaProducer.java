package ru.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.models.Notifications;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("kafka")
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";
    @PostMapping(path="/publish",consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public String sendDataToKafka(@RequestBody Notifications applicationData){
      //  System.out.println(applicationData.toString());
        kafkaTemplate.send(TOPIC, applicationData);
        return "Published succesfully";
    }



}
