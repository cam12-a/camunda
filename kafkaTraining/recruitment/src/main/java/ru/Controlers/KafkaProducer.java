package ru.Controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.Model.ApplicationData;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("kafka")
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, ApplicationData> kafkaTemplate;
    private static final String TOPIC="recruitment_app_json";
    @PostMapping(path="/publish",consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public String sendDataToKafka(@RequestBody ApplicationData applicationData){
        kafkaTemplate.send(TOPIC, applicationData);
        return "Published succesfully";
    }

    @GetMapping("/publish/{message}")
    public String sendMessage(@PathVariable("message") String message) {
         //   kafkaTemplate.send(TOPIC, message);
            return message;
    }



}
