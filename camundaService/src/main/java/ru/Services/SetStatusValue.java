package ru.Services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
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

    //private List<Task> tasksList=processDetails.taskList();


    public boolean validateStatusValue(String status){

        if(status.equals("Approved") || status.equals("Cancelled") || status.equals("Executed"))
            return true;
        else
            return false;

    }

    public void statusUpdateByManagerOrAssistance(String statusValue){

        if(validateStatusValue(statusValue)) {
            lastStatusValue = applicationData.getStatus();
            List<Task> taskList = new ArrayList<>();

            if (applicationData.getMapping().getStatusModel().getApproved() != null && applicationData.getMapping()
                    .getStatusModel().getCancelled() == null)
                taskList = processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                        .getStatusModel().getApproved().getAuthorName(), applicationData.getDateFrom(), applicationData.getDateTo());
            else
                taskList = processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                        .getStatusModel().getCancelled().getAuthorName(), applicationData.getDateFrom(), applicationData.getDateTo());

            for (Task task : taskList) {
                ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                        .setVariable(task.getProcessInstanceId(), "status", statusValue);
            }
        }


    }

    public void moveProcessForward(Task task){

        /*
        ProcessEngines.getDefaultProcessEngine().getRuntimeService().signal(
                ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                .createExecutionQuery()
                .activityId(task.getId())
                .singleResult().getId());
                */

        ProcessEngines.getDefaultProcessEngine().getTaskService().complete(task.getId());

    }




    public void deleteAssistantTaskAfterManagerAgreement(Task task){
        ProcessEngines.getDefaultProcessEngine().getTaskService().deleteTask(task.getId(),true); ;

    }

    public boolean accessPermission(Task task){

        if(task.getTaskDefinitionKey().equals("waitForManagerAgreement") || task.getTaskDefinitionKey().equals("waitForManagerAgreement"))
            return false;
        else
            return true;
    }

    public void statusUpdateByHrOrHrAssistant(String statusValue) {

        if (validateStatusValue(statusValue)) {

            lastStatusValue = applicationData.getStatus();
            List<Task> taskList = new ArrayList<>();

            if (applicationData.getMapping().getStatusModel().getApproved() != null && applicationData.getMapping().getStatusModel().getExecuted() == null)
                taskList = processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                        .getStatusModel().getExecuted().getAuthorName(), applicationData.getDateFrom(), applicationData.getDateTo());
            else
                taskList = processDetails.getTasksAppliedByAuthorName(applicationData.getMapping()
                        .getStatusModel().getExecuted().getAuthorName(), applicationData.getDateFrom(), applicationData.getDateTo());

            for (Task task : taskList) {
                if (accessPermission(task))
                    ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                            .setVariable(task.getProcessInstanceId(), "status", statusValue);
            }
        }
    }


}
