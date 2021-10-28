package ru.Listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.models.Notifications;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import java.util.HashMap;

import java.util.Map;

@Service
public class kafkaConsumer {

    @Autowired
    Notifications notifications;


   @KafkaListener(topics = "notification", groupId = "group_notification",containerFactory = "applicationKafkaListenerFactory")
    public void consume(Notifications notifications) throws ParseException {
        System.out.println("Consume message "+notifications.toString());


    }
}
