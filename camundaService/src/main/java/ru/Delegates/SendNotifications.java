package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.models.ApplicationData;
import ru.models.Mapping;
import ru.models.Notifications;

import javax.inject.Named;


@Named("SendNotifications")
public class SendNotifications implements JavaDelegate {

    @Autowired
    Mapping mapping;
    @Autowired
    ApplicationData applicationData;
    @Autowired
    Notifications notification;

    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Rejected");
        notification.setNotificationHeader("Кабинет руководителя");
        notification.setReceiverId(applicationData.getSubmittedBy());
        if(applicationData.isPerformedByManager())
            notification.setNotificationText("Ваша заявка была отклонена вашим руководителем");
        else
            notification.setNotificationText("Ваша заявка была отклонена помощником руководителя");

        kafkaTemplate.send(TOPIC,notification);
    }
}
