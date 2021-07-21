package ru.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.services.MyResAPI;

public class WhereToStart implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        MyResAPI myResAPI=new MyResAPI();
            if(delegateExecution.getVariable("source").equals("api"))
                myResAPI.normaStart();
    }
}
