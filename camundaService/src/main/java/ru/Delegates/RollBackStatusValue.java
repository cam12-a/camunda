package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.SetStatusValue;
import ru.models.ApplicationData;

import javax.inject.Named;


@Named("RollBackStatusValue")
public class RollBackStatusValue implements JavaDelegate {
    @Autowired
    SetStatusValue statusValue;
    @Autowired
    ApplicationData applicationData;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        applicationData.setStatus(statusValue.getLastStatusValue());
        delegateExecution.setVariable("status",statusValue.getLastStatusValue());

    }
}
