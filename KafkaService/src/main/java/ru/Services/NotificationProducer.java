package ru.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.models.Notifications;


@Service("NotificationProducer")
public class NotificationProducer {

    @Autowired
    KafkaProperties kafkaProperties;
    @Autowired
    KafkaTemplate<String, Notifications> notificationsKafkaTemplate;
    private static final String TOPIC_NAME = "notifications";

    public String sendNotification(@RequestBody Notifications notifications) {
        String errorMessage="";
        try {
            notificationsKafkaTemplate.send(TOPIC_NAME, notifications);
            errorMessage="Сообщение успешно отправлено операторам";
        } catch (Exception e) {
            errorMessage="Возникла ошибка при отправлении сообщения";
        }
        return errorMessage;

    }

}
