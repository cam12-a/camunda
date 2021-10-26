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
        String operator="";
        String assistant="";
        for(Map.Entry<String, String> entry : assignTask.getAssigner(users.get(0).getId()).entrySet()) {
            operator=entry.getKey();
            assistant=entry.getValue();
        }

        delegateTask.setAssignee(users.get(0).getId());
        assignTask.assignTaskToOperations(applicationData.isParallelWay(), "waitForHRAgreementInParallelProcess","waitForHRAssistantAgreement",users.get(0).getId(),assistant,delegateTask);

    }
}
