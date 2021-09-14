package ru.Services;



import lombok.SneakyThrows;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Model.ApplicationData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


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
                    .processDefinitionKey("security")
                    .latestVersion().singleResult();

            List<ProcessInstance> processInstance=this.processToExecuteAutomatically();


            for(ProcessInstance pr: processInstance){

                VariableInstance variableInstance=runtimeService.createVariableInstanceQuery()
                        .processInstanceIdIn(pr.getId())
                        .variableName("applicationGUI")
                        .singleResult();

                if( variableInstance.getValue().equals(appNumber)){
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

                    applicationData.setJobPlace(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("jobPlace").singleResult().getTypedValue().getValue().toString());


                    applicationData.setStage(Integer.parseInt(runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("stage").singleResult().getTypedValue().getValue().toString()));

                    break;

                }
            }
        }

    }


    public List<ProcessInstance> processToExecuteAutomatically()
    {

        ProcessDefinition processDefinition=ProcessEngines.getDefaultProcessEngine().getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey("security")
                .latestVersion().singleResult();

        return ProcessEngines.getDefaultProcessEngine().getRuntimeService().createProcessInstanceQuery()
                .processDefinitionId(processDefinition.getId())
                .active()
                .list();
    }

    public void setStatusValue(String statusValue, String appNumber) {

        List<ProcessInstance> processInstance = this.processToExecuteAutomatically();
        for (ProcessInstance pr : processInstance) {

            VariableInstance variableInstance = ProcessEngines.getDefaultProcessEngine().getRuntimeService().createVariableInstanceQuery()
                    .processInstanceIdIn(pr.getId())
                    .variableName("applicationGUI")
                    .singleResult();

            if (variableInstance.getValue().equals(appNumber)) {

                ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                        .setVariable(pr.getId(),"status",statusValue);
                break;

            }
        }


    }
    public void completeTask(String appNumber, Map<String,Object> variables)
    {
        List<ProcessInstance> processInstance = this.processToExecuteAutomatically();
        for (ProcessInstance pr : processInstance) {

            VariableInstance variableInstance = ProcessEngines.getDefaultProcessEngine().getRuntimeService().createVariableInstanceQuery()
                    .processInstanceIdIn(pr.getId())
                    .variableName("applicationGUI")
                    .singleResult();

            if (variableInstance.getValue().equals(appNumber)) {
                TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
                Task task=taskService.createTaskQuery()
                        .processInstanceId(pr.getId())
                        .active()
                        .singleResult();

                System.out.println("idtask "+task.getId());
                taskService.completeWithVariablesInReturn(task.getId(),variables,true);

                break;
            }
        }

    }


}
