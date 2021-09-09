package ru.Services;


import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CheckProcessExist")
public class CheckProcessExist {

    public boolean isProcessExist(String appNumber)
    {
        ProcessEngine processEngines=ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngines.getHistoryService();;
        RepositoryService repositoryService=processEngines.getRepositoryService();

        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("checkSecurity")
                .latestVersion().singleResult();

        List<HistoricProcessInstance> historicProcessInstance= historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId(processDefinition.getId())
                .list();



        for(int i=0;i<historicProcessInstance.size();i++)
        {
            HistoricVariableInstance historicVariableInstance=historyService.createHistoricVariableInstanceQuery()
                    .processDefinitionId(processDefinition.getId())
                    .processInstanceIdIn(historicProcessInstance.get(i).getId())
                    .variableName("applicationGUI")
                    .singleResult();


            if(appNumber.equals(historicVariableInstance.getValue()))
                return true;
        }

        return false;

    }
}
