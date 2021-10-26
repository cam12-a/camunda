package ru.Services;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.models.ApplicationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AssignTask")
public class AssignTask {
    @Autowired
    CallExternalService callExternalService;
    @Autowired
    ApplicationData applicationData;

    public void assignTaskToOperations(boolean parallelWay,String managerActivityId, String assistantActivityId,String manager, String assistant, DelegateTask delegateTask){
        Map<String, String> operators = new HashMap<>();

        if (parallelWay) {
            if(delegateTask.getTaskDefinitionKey().equals(managerActivityId))
                delegateTask.setAssignee(manager);
            if(delegateTask.getTaskDefinitionKey().equals(assistantActivityId)){
                operators= (Map<String, String>) callExternalService.executeExternalService("http://assistantService:8085/OperatorAssistantList/"+manager,operators);
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


    public boolean UserCantPerformTask(Task task, String assignedId){
        if(task.getAssignee().equals(assignedId))
            return true;
        else
            return false;
    }

    public Group getUserGroupDetails(String userId){
        return ProcessEngines.getDefaultProcessEngine()
                .getIdentityService()
                .createGroupQuery()
                .groupMember(userId)
                .singleResult();
    }


    public Map<String,String> getAssignerByEmployeeId(String userId){

        String operator="";
        Map<String, String> operators=new HashMap<>();
        //Получение руководителя сотрудника, id руководителя будет в переменной operator
        operators= (Map<String, String>) callExternalService.executeExternalService("assistantService:8085/OperatorAssistantList/"+userId,operators);
        operator=operators.get(userId);
        //Получение помощника руководителя
        operators= (Map<String, String>) callExternalService.executeExternalService("assistantService:8085/OperatorAssistantList/"+operators.get(userId),operators);
        System.out.println("op ass "+operators);



        return  operators;
    }

    public Map<String, String> getAssigner(String userId){
        String operator="";
        Map<String, String> operators=new HashMap<>();
        //Получение руководителя сотрудника, id руководителя будет в переменной operator
        operators= (Map<String, String>) callExternalService.executeExternalService("assistantService:8085/OperatorAssistantList/"+userId,operators);
        operator=operators.get(userId);
        return operators;
    }

    public void getWay(String assistant, DelegateExecution delegateExecution){
        if(assistant==null){
            applicationData.setParallelWay(false);
            delegateExecution.setVariable("parallelWay",false);
        }
        else {
            applicationData.setParallelWay(true);
            delegateExecution.setVariable("parallelWay", true);
        }
        System.out.println("way value "+applicationData.isParallelWay());
    }


}
