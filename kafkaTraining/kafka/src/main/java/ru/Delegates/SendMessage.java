package ru.Delegates;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Connector.SendStatus;
import ru.Model.ApplicationData;
import ru.Services.CloseApplication;

import javax.inject.Named;
import java.util.List;


@Named("SendMessage")
public class SendMessage implements JavaDelegate {


    @Autowired
    SendStatus sendStatus;

    @Autowired
    ApplicationData applicationData;

    @Autowired
    CloseApplication closeApplication;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        //sendStatus.sendStatus("http://localhost:7070/appData/"+ applicationData.getApplicationGUI()+"/");

        List<ProcessInstance> processInstance=delegateExecution.getProcessEngine()
                .getRuntimeService().createProcessInstanceQuery()
                .processDefinitionKey("process")
                .active()
                .list();


        for(ProcessInstance pr: processInstance) {

            VariableInstance variableInstance=ProcessEngines.getDefaultProcessEngine()
                    .getRuntimeService().createVariableInstanceQuery()
                    .processInstanceIdIn(pr.getId())
                    .variableName("applicationGUI")
                    .singleResult();

            if(variableInstance.getTypedValue().getValue().equals(delegateExecution.getVariable("applicationGUI"))){



               ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                        .setVariable(pr.getId(),"status",delegateExecution.getVariable("status").toString());

                delegateExecution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("startReview")
                        .processInstanceId(pr.getId()).correlateAllWithResult();

            }

        }


    }
}
