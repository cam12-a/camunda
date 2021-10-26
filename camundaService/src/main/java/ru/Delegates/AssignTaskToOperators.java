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
import ru.models.Mapping;

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
   @Autowired
   Mapping mapping;

    @Override
    public void notify(DelegateTask delegateTask) {

        Map<String, String> operators = new HashMap<>();

        String operator="";
        String assistant="";



            operator = "";
            assistant = "";
            for (Map.Entry<String, String> entry : assignTask.getAssignerByEmployeeId(applicationData.getSubmittedBy()).entrySet()) {
                operator = entry.getKey();
                assistant = entry.getValue();

            }
            assistant = operators.get(operator);
            delegateTask.setAssignee(operator);
            assignTask.assignTaskToOperations(applicationData.isParallelWay(), "waitForManagerAgreementInParallelProcess", "waitForManagerAssistantAgreement", operator, assistant, delegateTask);


        if(delegateTask.getTaskDefinitionKey().equals("waitForHRAgreementInParallelProcess") || delegateTask.getTaskDefinitionKey().equals("waitForHRAssistantAgreement")
        || delegateTask.getTaskDefinitionKey().equals("waitForHRAgreementInProcess")){
            List<User> users=assignTask.getUserByGroupId("hrGroup");
            operators=new HashMap<>();
            operator="";
            assistant="";
            for(Map.Entry<String, String> entry : assignTask.getAssigner(users.get(0).getId()).entrySet()) {
                operator=entry.getKey();
                assistant=entry.getValue();
            }

            delegateTask.setAssignee(users.get(0).getId());
            assignTask.assignTaskToOperations(applicationData.isParallelWay(), "waitForHRAgreementInParallelProcess","waitForHRAssistantAgreement",users.get(0).getId(),assistant,delegateTask);
        }

    }



}
