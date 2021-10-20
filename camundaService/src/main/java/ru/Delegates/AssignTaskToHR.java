package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.models.ApplicationData;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Named("AssignTaskToHR")
public class AssignTaskToHR implements TaskListener {

    @Autowired
    AssignTask assignTask;

    @Autowired
    CallExternalService callExternalService;

    @Autowired
    ApplicationData applicationData;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<User> users=assignTask.getUserByGroupId("hrGroup");
        Map<String, String> operators=new HashMap<>();
        String assistant="";
        operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+users.get(0).getId(),operators);
        assistant=operators.get(users.get(0).getId());

        delegateTask.setAssignee(users.get(0).getId());
        assignTask.assignTaskToOperations(applicationData.isParallelWay(), "waitForHRAgreementInParallelProcess","waitForHRAssistantAgreement",users.get(0).getId(),assistant,delegateTask);

    }
}
