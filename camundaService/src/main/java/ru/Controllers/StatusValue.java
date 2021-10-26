package ru.Controllers;


import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Services.AssignTask;
import ru.Services.ProcessDetails;
import ru.Services.SetStatusValue;
import ru.models.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class StatusValue {

    @Autowired
    SetStatusValue setStatusValue;
    @Autowired
    ProcessDetails processDetails;
    @Autowired
    ApplicationData applicationData;
    @Autowired
    AssignTask assignTask;
    @Autowired
    Mapping mapping;

    @PostMapping(value="/status/")
    public void changeStatus(@RequestBody Mapping statusModel){
        mapping.setStatusModel(statusModel.getStatusModel());

        List<Task> taskList=new ArrayList<>();
       applicationData.setPerformedByManager(false);


        String[] parseStrToDate=statusModel.getStatusModel().getDateFrom().toString().split("-");

        Calendar calendar = new GregorianCalendar( Integer.valueOf(parseStrToDate[0]), Integer.valueOf(parseStrToDate[1])-1 , Integer.valueOf(parseStrToDate[2]));
        Date date1 = calendar.getTime();
        System.out.println(date1);
        parseStrToDate=statusModel.getStatusModel().getDateTo().toString().split("-") ;
        calendar=new GregorianCalendar( Integer.valueOf(parseStrToDate[0]), Integer.valueOf(parseStrToDate[1])-1 , Integer.valueOf(parseStrToDate[2]));
        Date date2=calendar.getTime();
        System.out.println(date2);



        taskList=processDetails.getTasksAppliedByAuthorName("demo",date1,date2);

        for(Task task : taskList){
            String variableInstance="";
           //Определим роль пользователя
            Group group=assignTask.getUserGroupDetails(statusModel.getStatusModel().getAssignedTo());
            /**
             *  Проверяем заявка была ли ранее согласована руководителем или кодравиком, если да,
             *  то при согласовании помощником изменения не будут выполнены
             */
            variableInstance= (String) ProcessEngines.getDefaultProcessEngine().getTaskService()
                    .getVariable(task.getId(),"status");

           // System.out.println(task+"taskdefkey "+task.getTaskDefinitionKey());



           if((assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("assistant") || assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("hrAssistant")) &&
                   assignTask.UserCantPerformTask(task,statusModel.getStatusModel().getAssignedTo())){
                setStatusValue.updateStatus(task,statusModel.getStatusModel().getStatus());
                applicationData.setStatus(statusModel.getStatusModel().getStatus());
                //двигаем процесс на следующий шаг
                setStatusValue.moveProcessForward(task);
            }

            //Согласование заявки руководителем или кадровиком
            if(assignTask.UserCantPerformTask(task,statusModel.getStatusModel().getAssignedTo()) &&
                    (assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("operator") || assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("hrGroup"))){
                System.out.println("manager ");
                setStatusValue.updateStatus(task,statusModel.getStatusModel().getStatus());
                applicationData.setStatus(statusModel.getStatusModel().getStatus());
                variableInstance= (String) ProcessEngines.getDefaultProcessEngine().getTaskService()
                        .getVariable(task.getId(),"status");
                //Закыртие задачи у помощника
                applicationData.setPerformedByManager(true);
                //System.out.println("veri " + task + " status " + variableInstance+" assi "+task.getAssignee());
                //двигаем процесс на следующий шаг
                setStatusValue.moveProcessForward(task);

            }
            if(applicationData.isPerformedByManager()){
                if(task.getTaskDefinitionKey().equals("waitForManagerAssistantAgreement") || task.getTaskDefinitionKey().equals("waitForHRAssistantAgreement")){
                    System.out.println("deleting "+task.getTaskDefinitionKey());
                    if(assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("operator"))
                        setStatusValue.deleteAssistantTaskAfterManagerAgreement("waitForManagerAssistantAgreement",task);
                    if(assignTask.getUserGroupDetails(mapping.getStatusModel().getAssignedTo()).getId().equals("hrGroup"))
                        setStatusValue.deleteAssistantTaskAfterManagerAgreement("waitForHRAssistantAgreement",task);

                }

            }






        }




        System.out.println(statusModel.toString());
    }
}
