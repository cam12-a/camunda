package ru.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.models.Notifications;

@Service("LogNotifications")
public class LogNotifications {
    private final Logger logger= LoggerFactory.getLogger(Notifications.class);
    public void setLogger() {
        logger.info("Notifications ",logger);
        logger.error("This is an error");
    }
}
