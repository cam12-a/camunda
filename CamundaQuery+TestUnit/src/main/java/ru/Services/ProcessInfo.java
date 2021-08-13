package ru.Services;


import lombok.Data;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;

import java.util.*;

@Data
public  class ProcessInfo {
    private ProcessEngine processEngines;
    private String processKey;
    private int amountProcess=0;
    private List<String> processVariables= new ArrayList();

    public ProcessInfo(String processKey)
    {
        this.processKey=processKey;
    }

    public String  getUserID()
    {
      try {
          ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
          IdentityService identityService = processEngine.getIdentityService();
          return identityService.getCurrentAuthentication().getUserId();
      }catch (Exception e) {
          ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
         User user= processEngine.getIdentityService().createUserQuery().userId("alseny_initiator1").singleResult();
          return user.getId();

      }

    }

    public int getAmountProcess(String userID,String processDefinitionName) {
        return this.getAllFinishedProcessInstances(userID,processDefinitionName).size();
    }

    public List<HistoricProcessInstance> getAllFinishedProcessInstances(String userID, String processDefinitionName) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();;
        RepositoryService repositoryService = processEngine.getRepositoryService();


        ProcessDefinition myProcessDefinition =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionName(processDefinitionName)
                        .latestVersion() // we are only interested in the latest version
                        .singleResult();

        List<HistoricProcessInstance> processInstances =
                historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionId(myProcessDefinition.getId())
                        .startedBy(userID)
                        .list();


        return processInstances;
    }

    public ProcessDefinition getProcessDefinition(String processDefinitionName)
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition myProcessDefinition =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionName(processDefinitionName)
                        .latestVersion() // we are only interested in the latest version
                        .singleResult();
        return myProcessDefinition;

    }


    public  List<String> getProcessVariables(String userID, String processDefinitionName) {
        Map<String, Object> processVariable=new HashMap<>();
        List<String> A =new ArrayList<>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<HistoricProcessInstance> processInstances=this.getAllFinishedProcessInstances(userID,processDefinitionName);
        ProcessDefinition myProcessDefinition=this.getProcessDefinition(processDefinitionName);
        if(processInstances.size()==0)
            return new ArrayList<>();
        for(int i=0;i<processInstances.size()-1;i++)
        {
            try {

                processVariable.put("vacationStartDate",this.getAllProcessVariables(myProcessDefinition.getId(),processInstances.get(i).getId(),"vacationStartDate").getValue());
                processVariable.put("vacationEndDate",this.getAllProcessVariables(myProcessDefinition.getId(),processInstances.get(i).getId(),"vacationEndDate").getValue());
                processVariable.put("vacationType",this.getAllProcessVariables(myProcessDefinition.getId(),processInstances.get(i).getId(),"vacationType").getValue());
                //processVariable.put("applicationGUI",this.getAllProcessVariables(myProcessDefinition.getId(),processInstance.getId(),"applicationGUI").getValue());
                this.processVariables.add(processVariable.toString());

               // processVariable.forEach((key,value)->System.out.println(key+" : "+value));

            }catch (Exception ex) {
                System.out.println(ex.getMessage()+" "+ex.getLocalizedMessage()+" "+ex.getCause()+" "+ex.getStackTrace().toString());
               continue;
            }
        }

        return this.processVariables;

    }


    public HistoricVariableInstance getAllProcessVariables(String processDefinitionID, String processInstanceID, String variableName)
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
       return historyService.createHistoricVariableInstanceQuery()
                .processDefinitionId(processDefinitionID)
                .processInstanceIdIn(processInstanceID)
                .variableName(variableName)
                .singleResult();
    }







}
