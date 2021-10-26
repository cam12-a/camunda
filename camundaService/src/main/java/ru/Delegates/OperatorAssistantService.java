package ru.Delegates;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.Services.ProcessDetails;
import ru.Services.SendNotificationsService;
import ru.models.ApplicationData;
import ru.models.Mapping;
import ru.models.Notifications;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("OperatorAssistantService")
public class OperatorAssistantService implements JavaDelegate {

    @Autowired
    CallExternalService callExternalService;
    @Autowired
    ProcessDetails processDetails;
    @Autowired
    AssignTask assignTask;
    @Autowired
    ApplicationData applicationData;
    @Autowired
    Notifications notifications;
    @Autowired
    SendNotificationsService sendNotifications;
    @Autowired
    Mapping mapping;

    @Autowired
    private KafkaTemplate<String, Notifications> kafkaTemplate;
    private static final String TOPIC="notification";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

       String operator="";
       String assistant="";

      // if(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication()!=null && mapping.getStatusModel().getAuthorName()!=null &&
       //        delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId().equals(mapping.getStatusModel().getAuthorName())){
           for (Map.Entry<String, String> entry : assignTask.getAssignerByEmployeeId(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId()).entrySet()) {
               operator = entry.getKey();
               assistant = entry.getValue();

           }
           System.out.println("here");


           assignTask.getWay(assistant, delegateExecution);

           applicationData.setSubmittedBy(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId());


           try{
               sendNotifications.notifyOperator("Заявка на отсутствие в рабочее время", "Заявка была согласована руководителем, пожалуйста, обработайте ее", operator, assistant);
           }catch (Exception ex){
               applicationData.setErrorWhilePushingKafkaMessage("error");
           }


    }


}
