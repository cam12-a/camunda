package ru.Services;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AssignTask")
public class AssignTask {
    @Autowired
    CallExternalService callExternalService;

    public void assignTaskToOperations(boolean parallelWay,String managerActivityId, String assistantActivityId,String manager, String assistant, DelegateTask delegateTask){
        Map<String, String> operators = new HashMap<>();

        if (parallelWay) {
            if(delegateTask.getTaskDefinitionKey().equals(managerActivityId))
                delegateTask.setAssignee(manager);
            if(delegateTask.getTaskDefinitionKey().equals(assistantActivityId)){
                operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+manager,operators);
                delegateTask.setAssignee(operators.get(manager));
            }
        }

    }

    public List<User> getUserByGroupId(String groupId){
       return  ProcessEngines.getDefaultProcessEngine()
                .getIdentityService()
                .createUserQuery()
                .memberOfGroup(groupId)
                .list();
    }
}
