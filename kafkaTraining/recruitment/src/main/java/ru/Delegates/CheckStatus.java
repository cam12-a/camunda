package ru.Delegates;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.connect.spi.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Connectors.GetStatusValue;
import ru.Model.ApplicationData;

import javax.inject.Named;
import java.util.List;


@Named("CheckStatus")
public class CheckStatus implements JavaDelegate {


    @Autowired
    GetStatusValue getStatusValue;
    @Autowired
    ApplicationData applicationData;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

       applicationData= getStatusValue.sendStatus(delegateExecution.getVariable("applicationGUI").toString());
       delegateExecution.setVariable("status",applicationData.getStatus());
       System.out.println(applicationData.toString());


    }
}
