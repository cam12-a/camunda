package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Model.ApplicationData;

import javax.inject.Named;
import java.util.Date;

@Named("RandomGUI")
public class RandomGUI implements JavaDelegate {

    @Autowired
    ApplicationData applicationData;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Date date=new Date();
        String GUID=Long.toString(date.getTime());
        delegateExecution.setVariable("applicationGUI",GUID);


    }
}
