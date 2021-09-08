package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Model.ApplicationData;

import javax.inject.Named;

@Named("GetAppData")
public class GetAppData implements JavaDelegate {


    @Autowired
    ApplicationData applicationData;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.setVariable("applicationGUI",applicationData.getApplicationGUI());
        delegateExecution.setVariable("dateBirth",applicationData.getDateBirth());
        delegateExecution.setVariable("firstName",applicationData.getFirstName());
        delegateExecution.setVariable("lastName",applicationData.getLastName());
        delegateExecution.setVariable("jobPlace",applicationData.getJobPlace());
        delegateExecution.setVariable("stage",applicationData.getStage());
        delegateExecution.setVariable("status",applicationData.getStatus());






    }
}
