package ru.Services;



import lombok.SneakyThrows;
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
import ru.Model.ApplicationData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service("CloseApplication")
public class CloseApplication {

    @Autowired
    CheckProcessExist checkProcessExist;

    @Autowired
    ApplicationData applicationData;

    @SneakyThrows
    public void closeApplication(String appNumber)
    {

        if(checkProcessExist.isProcessExist(appNumber)){

            ProcessEngine processEngines=ProcessEngines.getDefaultProcessEngine();
            RuntimeService runtimeService=processEngines.getRuntimeService();
            RepositoryService repositoryService=processEngines.getRepositoryService();

            ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("checkSecurity")
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

                if(variableInstance.getValue().equals(appNumber)){
                    applicationData.setApplicationGUI(variableInstance.getTypedValue().getValue().toString());

                    applicationData.setFirstName(runtimeService.createVariableInstanceQuery()
                    .processInstanceIdIn(pr.getId())
                    .variableName("firstName").singleResult().getTypedValue().getValue().toString());

                    applicationData.setLastName(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("lastName").singleResult().getTypedValue().getValue().toString());

                    applicationData.setStatus(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("status").singleResult().getTypedValue().getValue().toString());

                    applicationData.setName(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("name").singleResult().getTypedValue().getValue().toString());


                    applicationData.setStage(Integer.parseInt(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("stage").singleResult().getTypedValue().getValue().toString()));
                    DateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
                    DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

                    applicationData.setDateBirth(formatter1.parse(formatter1.format(formatter.parse(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("dateBirth").singleResult().getTypedValue().getValue().toString()))));



                    /*Execution execution=runtimeService.createExecutionQuery()
                            .processDefinitionId(processDefinition.getId())
                            .processInstanceId(pr.getId())
                            .activityId("externalSystemSolution")
                            .singleResult();

                    runtimeService.signal(execution.getId());*/

                }
            }
        }

    }




}
