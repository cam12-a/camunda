package ru.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.models.ApplicationData;
import ru.models.Notifications;

@Service("SendNotificationsService")
public class SendNotificationsService {

    @Autowired
    Notifications notifications;
    @Autowired
    ApplicationData applicationData;

    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";

    public void notifyOperator(String header, String message,String operator,String assistant){
        notifications.setNotificationHeader(header);
        notifications.setNotificationText(message+" "+applicationData.getSubmittedBy());

        if(assistant==null){
            notifications.setReceiverId(operator);
            kafkaTemplate.send(TOPIC,notifications);
        }
        else{
            notifications.setReceiverId(operator);
            kafkaTemplate.send(TOPIC,notifications);
            notifications.setReceiverId(assistant);
            kafkaTemplate.send(TOPIC,notifications);
        }
    }

    public void notifyOwner(String owner, String header, String message){
        notifications.setNotificationHeader(header);
        notifications.setReceiverId(applicationData.getSubmittedBy());
        if(applicationData.isPerformedByManager())
            notifications.setNotificationText(message);
        else
            notifications.setNotificationText(message);

        kafkaTemplate.send(TOPIC,notifications);
    }
}
