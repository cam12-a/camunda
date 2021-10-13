package ru.Services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.models.ApplicationData;

import java.util.ArrayList;
import java.util.List;


@Service("SetStatusValue")
public class SetStatusValue {

    private String lastStatusValue;

    @Autowired
    ProcessDetails processDetails;
    @Autowired
    ApplicationData applicationData;

    private List<Task> tasksList=processDetails.taskList();


    public String validateStatusValue(Task task){
        String errorMessage="";
        if(!this.accessPermission(task))
            errorMessage+="Вы не имеете право согласовать/оклонить заявку, на данный момент задача у руководителя и его помщника";


        return errorMessage;
    }

    public void statusUpdateByManagerOrAssistance(String statusValue){

        lastStatusValue =applicationData.getStatus();
        List<Task> taskList=new ArrayList<>();

        if(applicationData.getMapping().getStatusModel().getApproved()!=null && applicationData.getMapping()
        .getStatusModel().getCancelled()==null)
            taskList=processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                    .getStatusModel().getApproved().getAuthorName(),applicationData.getDateFrom(),applicationData.getDateTo());
        else
            taskList=processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                    .getStatusModel().getCancelled().getAuthorName(),applicationData.getDateFrom(),applicationData.getDateTo());

        for(Task task:taskList){
            ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                    .setVariable(task.getProcessInstanceId(),"status",statusValue);
        }


    }

    public void moveProcessForward(Task task){
        ProcessEngines.getDefaultProcessEngine().getRuntimeService().signal(
                ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                .createExecutionQuery()
                .activityId(task.getId())
                .singleResult().getId());

    }

    public boolean accessPermission(Task task){
        if(task.getTaskDefinitionKey().equals("waitFormanagerAgreement") || task.getTaskDefinitionKey().equals("waitFormanagerAgreement"))
            return false;
        else
            return true;
    }

    public void statusUpdateByHrOrHrAssistant(String statusValue){
         lastStatusValue = applicationData.getStatus();
        List<Task> taskList=new ArrayList<>();

        if(applicationData.getMapping().getStatusModel().getApproved()!=null && applicationData.getMapping().getStatusModel().getExecuted()==null)
            taskList = processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                    .getStatusModel().getExecuted().getAuthorName(), applicationData.getDateFrom(), applicationData.getDateTo());
        else
            taskList=processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                    .getStatusModel().getExecuted().getAuthorName(),applicationData.getDateFrom(),applicationData.getDateTo());

        for(Task task:taskList){
            if(accessPermission(task))
                ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                .setVariable(task.getProcessInstanceId(),"status",statusValue);
        }
    }


}
