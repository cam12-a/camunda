package ru.Services;


import camundajar.impl.scala.concurrent.impl.FutureConvertersImpl;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountMenAndWomen {
    private int countMale=0;
    private int countFemale=0;
    public String MenAndWomenAmount(String processKey){

       ProcessEngine processEngines=ProcessEngines.getDefaultProcessEngine();
       RuntimeService runtimeService= processEngines.getRuntimeService();
       RepositoryService repositoryService = processEngines.getRepositoryService();

        ProcessDefinition processDefinition =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(processKey)
                        .latestVersion()
                        .singleResult();

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionId(processDefinition.getId())
                        .active() // we only want the unsuspended process instances
                        .list();

        for(ProcessInstance pr: processInstances) {
            VariableInstance variables=
                    runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(pr.getId())
                            .variableName("Gender")
                            .singleResult();
            try {
                if (variables.getTypedValue().getValue().equals("male"))
                    this.countMale++;
                if(variables.getTypedValue().getValue().equals("female"))
                    this.countFemale++;
                System.out.println(variables.getTypedValue().getValue());
            }catch (Exception ex)
            {
                return ex.getMessage();
            }

           // System.out.println(variables.getTypedValue().getValue());
           // System.out.println(pr.getId());
        }

        return "В "+ processKey+ " сгенерировалось "+ this.countMale+" мужчин и "+ " "+this.countFemale+" женщин";
    }
}
