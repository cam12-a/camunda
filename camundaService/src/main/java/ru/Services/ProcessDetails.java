package ru.Services;


import org.camunda.bpm.engine.ProcessEngines;

import org.camunda.bpm.engine.history.HistoricProcessInstance;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class ProcessDetails {


    public List<Task> taskList(){

        return ProcessEngines.getDefaultProcessEngine().getTaskService()
                .createTaskQuery()
                .active()
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
