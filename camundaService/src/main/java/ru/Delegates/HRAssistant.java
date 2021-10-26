package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.Services.SendNotificationsService;
import ru.models.ApplicationData;
import ru.models.Mapping;
import ru.models.Notifications;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Named("HRAssistant")
public class HRAssistant implements JavaDelegate {

    @Autowired
    CallExternalService callExternalService;

    @Autowired
    ApplicationData applicationData;

    @Autowired
    AssignTask assignTask;
    @Autowired
    Notifications notifications;
    @Autowired
    Mapping mapping;
    @Autowired
    OperatorAssistantService operatorAssistantService;
    @Autowired
    SendNotificationsService sendNotifications;
    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


       List<User> users=assignTask.getUserByGroupId("hrGroup");

        String operator="";
        String assistant="";
        for(Map.Entry<String, String> entry : assignTask.getAssigner(users.get(0).getId()).entrySet()) {
            operator=entry.getKey();
            assistant=entry.getValue();
        }
        System.out.println("ass "+assistant+" op "+operator);


        if(assistant==null){
            applicationData.setParallelWay(false);
        }
        else {
            applicationData.setParallelWay(true);
        }
        delegateExecution.setVariable("parallelWay",applicationData.isParallelWay());

        try {
            sendNotifications.notifyOperator(mapping.getStatusModel().getNotificationHeader(),mapping.getStatusModel().getNotificationText(),operator,assistant);
        }catch (Exception e){
            applicationData.setErrorWhilePushingKafkaMessage("error");
        }



    }
}
