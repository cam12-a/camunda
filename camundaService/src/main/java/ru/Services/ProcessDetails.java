package ru.Services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.camunda.bpm.BpmPlatform.getDefaultProcessEngine;



@Service
public class ProcessDetails {


    public List<Task> taskList(){
        return ProcessEngines.getDefaultProcessEngine().getTaskService()
                .createTaskQuery()
                .active()
                .list();
    }


    public List<VariableInstance> getVariableInstance(String taskID,String variableName, String processInstanceId){

        return ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                .createVariableInstanceQuery()
                .taskIdIn(taskID)
                .variableName(variableName)
                .processInstanceIdIn(processInstanceId)
                .list();
    }

    public List<Task> getTasksAppliedByAuthorName(String authorName, Date dateFrom, Date dateTO){

        List<Task> tasksList=this.taskList();
        List<Task> resultantTaskList=new ArrayList<>();
        List<HistoricProcessInstance> processInstances= ProcessEngines.getDefaultProcessEngine().getHistoryService()
                .createHistoricProcessInstanceQuery()
                .startedBy(authorName)
                .variableValueEquals("dateFrom",dateFrom)
                .variableValueEquals("dateTo",dateTO)
                .active()
                .list();

       for(Task task : tasksList){
           for(HistoricProcessInstance historicProcessInstance: processInstances){
               if(task.getProcessInstanceId().equals(historicProcessInstance.getId())){
                    resultantTaskList.add(task);
               }
           }
       }
       return  resultantTaskList;
    }





}
