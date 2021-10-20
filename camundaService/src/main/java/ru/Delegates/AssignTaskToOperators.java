package ru.Delegates;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.models.ApplicationData;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("AssignTaskToOperators")
public class AssignTaskToOperators implements TaskListener {

   @Autowired
    ApplicationData applicationData;
   @Autowired
    CallExternalService callExternalService;
   @Autowired
    AssignTask assignTask;

    @Override
    public void notify(DelegateTask delegateTask) {

        Map<String, String> operators = new HashMap<>();
        operators=(Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+applicationData.getSubmittedBy(),operators);
        String manager=operators.get(applicationData.getSubmittedBy());
        operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+manager,operators);

        String assistant=operators.get(manager);
        delegateTask.setAssignee(manager);
        assignTask.assignTaskToOperations(applicationData.isParallelWay(), "waitForManagerAgreementInParallelProcess","waitForManagerAssistantAgreement",manager,assistant,delegateTask);


    }


}
