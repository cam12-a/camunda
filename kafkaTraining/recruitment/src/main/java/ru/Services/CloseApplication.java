package ru.Services;



import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("CloseApplication")
public class CloseApplication {

    @Autowired
    CheckProcessExist checkProcessExist;

    public void closeApplication(String appNumber)
    {

        if(checkProcessExist.isProcessExist(appNumber)){

            ProcessEngine processEngines=ProcessEngines.getDefaultProcessEngine();
            RuntimeService runtimeService=processEngines.getRuntimeService();
            RepositoryService repositoryService=processEngines.getRepositoryService();

            ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("process")
                    .latestVersion().singleResult();


            List<ProcessInstance> processInstance=runtimeService.createProcessInstanceQuery()
                    .processDefinitionId(processDefinition.getId())
                    .active()
                    .list();

            for(ProcessInstance pr: processInstance){
                VariableInstance variableInstance=runtimeService.createVariableInstanceQuery()
                        .processInstanceIdIn(pr.getId())
                        .variableName("applicationGUI")
                        .singleResult();


               /* if(variableInstance.getTypedValue().getValue().equals(appNumber)){
                    Execution execution=runtimeService.createExecutionQuery()
                            .processDefinitionId(processDefinition.getId())
                            .processInstanceId(pr.getId())
                            .activityId("externalSystemSolution")
                            .singleResult();

                    runtimeService.signal(execution.getId());

                }*/
            }
        }

    }




}
