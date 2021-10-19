package ru.Delegates;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.models.ApplicationData;

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

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

       List<User> users=assignTask.getUserByGroupId("hrGroup");

        String operator="";
        Map<String, String> operators=new HashMap<>();
        //Получение помощника по id кадровика

        operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+users.get(0).getId(),operators);
        operator=operators.get(users.get(0).getId());
        //System.out.println("assistant in delegate "+operator);
       // System.out.println("HR delegate "+users.get(0));

       // applicationData.setSubmittedBy(users.get(0).getId());

        if(operator==null){
            applicationData.setParallelWay(false);

        }
        else {
            applicationData.setParallelWay(true);
        }
        delegateExecution.setVariable("parallelWay",applicationData.isParallelWay());

    }
}
