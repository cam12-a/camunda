package ru.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.models.Notifications;

@Service("ReadNotifications")
public class ReadNotifications {

    @Autowired
    KafkaProperties kafkaProperties;
    private static final String TOPIC_NAME = "Notifications";
    @KafkaListener(topics = "Notifications",groupId = "group_notification",containerFactory = "applicationKafkaListenerFactory")
    public void consume(Notifications notifications){
        System.out.println(notifications.toString());
    }

}
