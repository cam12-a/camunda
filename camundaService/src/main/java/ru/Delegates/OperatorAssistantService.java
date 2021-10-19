package ru.Delegates;


import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.AssignTask;
import ru.Services.CallExternalService;
import ru.Services.ProcessDetails;
import ru.models.ApplicationData;

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

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String operator="";
        Map<String, String> operators=new HashMap<>();
        //Получение руководителя сотрудника, id руководителя будет в переменной operator
        operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId(),operators);
        operator=operators.get(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId());
        //Получение помощника руководителя
        operators= (Map<String, String>) callExternalService.executeExternalService("http://localhost:8085/OperatorAssistantList/"+operators.get(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId()),operators);

        applicationData.setSubmittedBy(delegateExecution.getProcessEngine().getIdentityService().getCurrentAuthentication().getUserId());

        if(operators.get(operator)==null){
            applicationData.setParallelWay(false);
            delegateExecution.setVariable("parallelWay",false);
        }
        else {
            applicationData.setParallelWay(true);
            delegateExecution.setVariable("parallelWay", true);
        }

    }
}
