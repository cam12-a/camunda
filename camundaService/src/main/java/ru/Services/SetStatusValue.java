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
import java.util.Map;


@Service("SetStatusValue")
public class SetStatusValue {

    private String lastStatusValue;

    @Autowired
    ProcessDetails processDetails;
    @Autowired
    ApplicationData applicationData;



    public boolean validateStatusValue(String status){

        if(status.equals("Approved") || status.equals("Rejected") || status.equals("Executed"))
            return true;
        else
            return false;

    }



    public void moveProcessForward(Task task){

        try{
            ProcessEngines.getDefaultProcessEngine().getTaskService().complete(task.getId());
        }catch(Exception e){
            System.out.println("moved error "+e.getMessage());
        }

    }


    public void deleteAssistantTaskAfterManagerAgreement(Task task){

        try{
            ProcessEngines.getDefaultProcessEngine().getTaskService().deleteTask(task.getId(),true);
        }catch(Exception e){
            System.out.println("deleteEx "+e.getMessage());
        }

    }

    public boolean accessPermission(Task task){

        if(task.getTaskDefinitionKey().equals("waitForManagerAgreement") || task.getTaskDefinitionKey().equals("waitForManagerAgreement"))
            return false;
        else
            return true;
    }

    public void updateStatus(Task task,String statusValue) {

        if (validateStatusValue(statusValue)) {
            System.out.println("In Status Set");
            lastStatusValue = applicationData.getStatus();

                ProcessEngines.getDefaultProcessEngine().getTaskService()
                        .setVariable(task.getId(), "status", statusValue);

        }
    }

}
