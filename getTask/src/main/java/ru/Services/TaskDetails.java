package ru.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngines;


import org.camunda.bpm.engine.task.Task;

import org.springframework.stereotype.Service;
import ru.Parse.Mapping;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service("TaskDetails")
public class TaskDetails {
    private List<String> tasksID=new ArrayList<String>();
    List<Task> taskListByTaskDefinition=new ArrayList<>();
    List<Task> taskListByUserCandidate=new ArrayList<>();
    List<Task> taskListByProcessKey=new ArrayList<>();

    public void  getTasksID(Mapping mapping)
    {
        this.tasksID=new ArrayList<>();
        List<Task> taskByBusiness=this.getTaskByBusinessKey(mapping);
        List<Task> taskList=this.getTaskList();
        List<Task> taskListByApplicationNumber=this.getTaskByApplicationNumber(mapping);
        List<Task> currentTaskList=new ArrayList<>();
        List<Task> intersectionSet=new ArrayList<>();


        if(taskByBusiness.size()!=0){
            System.out.println("Bus");
            if(taskListByApplicationNumber.size()==0) {
                System.out.println("AppNum & ProcKey null");
               this.setTaskListId(taskByBusiness,"Bus");
            }
            else{
                intersectionSet=this.IntersectionOfTaskList(taskByBusiness,taskListByApplicationNumber);
                this.setTaskListId(intersectionSet,"Bus && appNum not null");
            }
        }
        else{
            taskListByUserCandidate=this.getTaskByUserAndCandidateUser(taskList,mapping);
            taskListByTaskDefinition=this.getTaskByTaskDefinitionKey(taskList,mapping);
            taskListByProcessKey=this.getTaskByProcessKey(taskList,mapping);

            /**
             * Необходимло найти пересечение taskListByUserCandidate,
             * taskListByTaskDefinition и taskListByProcessKey, резултат которого
             * будет использован для нахождения пересечения с taskListByApplicationNumber.
             * При нахождения пересения будем изменять пероначание значения множеств.
             */

            if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()==0){
                intersectionSet=taskListByTaskDefinition;
                intersectionSet = this.IntersectionOfTaskList(intersectionSet, taskListByProcessKey);
            }
            else if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()==0 && taskListByUserCandidate.size()!=0){
                intersectionSet=taskListByTaskDefinition;
                intersectionSet=this.IntersectionOfTaskList(intersectionSet,taskListByUserCandidate);
            }
            else if(taskListByTaskDefinition.size()==0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()!=0){
                intersectionSet=taskListByProcessKey;
                intersectionSet=this.IntersectionOfTaskList(intersectionSet,taskListByUserCandidate);

            }
            else if(taskListByUserCandidate.size()==0 && taskListByProcessKey.size()==0 && taskListByTaskDefinition.size()!=0)
                intersectionSet=taskListByTaskDefinition;
            else if(taskListByTaskDefinition.size()==0 && taskListByUserCandidate.size()==0 && taskListByProcessKey.size()!=0)
                intersectionSet=taskListByProcessKey;
            else if(taskListByTaskDefinition.size()==0 && taskListByProcessKey.size()==0 && taskListByUserCandidate.size()!=0)
                intersectionSet= taskListByUserCandidate;
            else if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()!=0){
                intersectionSet=taskListByTaskDefinition;
                intersectionSet=this.IntersectionOfTaskList(intersectionSet,taskListByProcessKey);
                intersectionSet=this.IntersectionOfTaskList(intersectionSet,taskListByUserCandidate);
            }
            else
                intersectionSet=intersectionSet;
            /**
             * Пересечение с можеством taskListByApplicationNumber
             * поскольку полей в JSON могут быть пустыми, делаем все для того чтобы не
             * получили пустое множеств, потому что пересечение любого множества с пустым даст
             * пустое
             *
             * */


            if(taskListByApplicationNumber.size()==0){
                this.setTaskListId(intersectionSet,"null Bus & null AppNum");
            }
            else{
                if(intersectionSet.size()!=0){
                    intersectionSet=this.IntersectionOfTaskList(intersectionSet,taskListByApplicationNumber);
                    this.setTaskListId(intersectionSet,"12");
                }
                else{
                    this.setTaskListId(taskListByApplicationNumber,"13");
                }
            }


        }



    }

    public void setTaskListId(List<Task> taskList, String forDebug){
        for (Task task : taskList) {
            tasksID.add(task.getId() + " d " + task.getName() + " " + task.getTaskDefinitionKey() + " " + task.getProcessDefinitionId() + " " + task.getCaseDefinitionId() + " " + task.getProcessInstanceId() + " " + task.getAssignee());
            System.out.println(forDebug+" " + task.getId() + " d " + task.getName() + " " + task.getTaskDefinitionKey() + " " + task.getProcessDefinitionId() + " " + task.getCaseDefinitionId() + " " + task.getProcessInstanceId() + " " + task.getAssignee());
        }
    }

    public List<Task> IntersectionOfTaskList(List<Task> firstSet,List<Task> secondSet){
        firstSet.retainAll(secondSet);
        return firstSet;
    }

    public List<Task> findCoincidence(List<Task> currentTaskList, Mapping mapping, List<Task> listToPrintByDefault){

        List<Task> resultingTaskList = new ArrayList<>();

        return resultingTaskList;

    }

    public List<Task> getTaskList(){
        return ProcessEngines.getDefaultProcessEngine().getTaskService().createTaskQuery()
                .active()
                .list();
    }

    public List<Task> getTaskByTaskDefinitionKey(List<Task> task, Mapping mapping)
    {
        List<Task> resultingTaskList = new ArrayList<>();
        for(Task task1:task){
            for(int proccessKey=0;proccessKey<mapping.getFilters().getTask_definition_key_in().size();proccessKey++){
                if(task1.getTaskDefinitionKey().equals(mapping.getFilters().getTask_definition_key_in().get(proccessKey))) {
                 //   tasksID.add(task1.getId() + " " + task1.getName() + " " + task1.getTaskDefinitionKey() + " " + task1.getProcessDefinitionId()
                    //        + " " + task1.getCaseDefinitionId() + " " + task1.getProcessInstanceId());
                    resultingTaskList.add(task1);
                }
            }
        }


        return resultingTaskList;
    }


    public List<Task> getTaskByUserAndCandidateUser(List<Task> task , Mapping mapping){
        List<Task> resultingTaskList = new ArrayList<>();
        for(Task task1:task){
            for(int i=0;i<mapping.getFilters().getUsers().size();i++){
                if(task1.getAssignee()!=null && task1.getAssignee().equals(mapping.getFilters().getUsers().get(i).getId())){

                   resultingTaskList.add(task1);
                }
                for(int j=0;j<mapping.getFilters().getUsers().get(i).getIdentity_type_in().size();j++){
                   if(task1.getAssignee()!=null && task1.getAssignee().equals(mapping.getFilters().getUsers().get(i).getIdentity_type_in().get(j))){

                        resultingTaskList.add(task1);
                   }
                }
            }
        }



        return resultingTaskList;
    }


    public List<Task> getTaskByBusinessKey(Mapping mapping){
        List<Task> resultingTaskList = new ArrayList<>();
        for(int i=0;i<mapping.getFilters().getBusiness_key_in().size();i++) {
            List<Task> task = ProcessEngines.getDefaultProcessEngine().getTaskService()
                    .createTaskQuery()
                    .processInstanceBusinessKey(mapping.getFilters().getBusiness_key_in().get(i))
                    .active()
                    .list();
            for(Task task1:task)
            {

                resultingTaskList.add(task1);
            }
        }

            taskListByTaskDefinition = this.getTaskByTaskDefinitionKey(resultingTaskList, mapping);
            taskListByProcessKey = this.getTaskByProcessKey(resultingTaskList, mapping);
            taskListByUserCandidate = this.getTaskByUserAndCandidateUser(resultingTaskList, mapping);


        return this.intersetionOfTreeSet(resultingTaskList,taskListByTaskDefinition,taskListByProcessKey,taskListByUserCandidate);
    }

    public List<Task> getTaskByProcessKey(List<Task> task,Mapping mapping){
        List<Task> resultingTaskList = new ArrayList<>();
        if(mapping.getParameters()!=null)
        for(Task task1:task){
            int index=task1.getProcessDefinitionId().indexOf(":");
            for(int i=0;i<mapping.getParameters().getShow_variables().size();i++){

                    if (task1.getProcessDefinitionId().substring(0, index).equals(mapping.getParameters().getShow_variables().get(i))) {

                       // System.out.println(task1.getProcessDefinitionId().substring(0, index));
                        resultingTaskList.add(task1);
                    }

                }
            }

        return resultingTaskList;
    }

    public List<Task> getTaskByApplicationNumber(Mapping mapping){
        List<Task> resultingTaskList = new ArrayList<>();
        if(mapping.getParameters()!=null) {
            for (int i = 0; i < mapping.getParameters().getShow_variables().size(); i++) {
                List<Task> task = ProcessEngines.getDefaultProcessEngine().getTaskService()
                        .createTaskQuery()
                        .processVariableValueEquals("applicationGUI", mapping.getParameters().getShow_variables().get(i))
                        .active()
                        .list();
                for(Task task1:task){

                    resultingTaskList.add(task1);

                }
            }
        }

        taskListByTaskDefinition = this.getTaskByTaskDefinitionKey(resultingTaskList, mapping);
        taskListByProcessKey = this.getTaskByProcessKey(resultingTaskList, mapping);
        taskListByUserCandidate = this.getTaskByUserAndCandidateUser(resultingTaskList, mapping);


        return this.intersetionOfTreeSet(resultingTaskList,taskListByTaskDefinition,taskListByProcessKey,taskListByUserCandidate);
    }


    public List<Task> intersetionOfTreeSet(List<Task> resultingTaskList, List<Task> taskListByTaskDefinition, List<Task> taskListByProcessKey, List<Task> taskListByUserCandidate){
       System.out.println("TaskDef Size "+taskListByTaskDefinition.size());
        if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()==0){
            resultingTaskList = this.IntersectionOfTaskList(resultingTaskList, taskListByProcessKey);
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByTaskDefinition);
        }
        else if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()==0 && taskListByUserCandidate.size()!=0){
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByUserCandidate);
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByTaskDefinition);
        }
        else if(taskListByTaskDefinition.size()==0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()!=0){
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByUserCandidate);
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByProcessKey);
        }
        else if(taskListByUserCandidate.size()==0 && taskListByProcessKey.size()==0 && taskListByTaskDefinition.size()!=0)
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByTaskDefinition);
        else if(taskListByTaskDefinition.size()==0 && taskListByUserCandidate.size()==0 && taskListByProcessKey.size()!=0)
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByProcessKey);
        else if(taskListByTaskDefinition.size()==0 && taskListByProcessKey.size()==0 && taskListByUserCandidate.size()!=0)
            resultingTaskList = this.IntersectionOfTaskList(resultingTaskList, taskListByUserCandidate);
        else if(taskListByTaskDefinition.size()!=0 && taskListByProcessKey.size()!=0 && taskListByUserCandidate.size()!=0){
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByTaskDefinition);
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByProcessKey);
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,taskListByUserCandidate);

        }
        else
            resultingTaskList=this.IntersectionOfTaskList(resultingTaskList,resultingTaskList);

        return resultingTaskList;
    }



}
