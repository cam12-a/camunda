package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.Services.AssignTask;
import ru.Services.SendNotificationsService;
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
    AssignTask assignTask;
    @Autowired
    SendNotificationsService sendNotificationsService;

    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //System.out.println("Rejected");
       // if(assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).equals("operator") || assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).equals("assistant")) {

            //sendNotificationsService.notifyOwner(applicationData.getSubmittedBy(),mapping.getStatusModel().getNotificationHeader(),mapping.getStatusModel().getNotificationText());
            notification.setNotificationHeader(mapping.getStatusModel().getNotificationHeader());
            notification.setReceiverId(applicationData.getSubmittedBy());
            if (applicationData.isPerformedByManager()) {

                notification.setNotificationText(mapping.getStatusModel().getNotificationText()+" a");
            }
            else {
                notification.setNotificationText(mapping.getStatusModel().getNotificationText()+" b");
            }
      //  }
      //  if(assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).equals("hrGroup") || assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).equals("hrAssisatnt")){

      //  }

        try {
            kafkaTemplate.send(TOPIC,notification);
        }catch (Exception e){
            applicationData.setErrorWhilePushingKafkaMessage("error");
        }
    }
}
