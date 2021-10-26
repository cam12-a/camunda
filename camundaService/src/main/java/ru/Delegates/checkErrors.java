package ru.Delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.models.ApplicationData;

import javax.inject.Named;

@Named("checkErrors")
public class checkErrors implements JavaDelegate {
    @Autowired
    ApplicationData applicationData;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if(applicationData.getErrorWhilePushingKafkaMessage()!=null && applicationData.getErrorWhilePushingKafkaMessage().equals("error")){
            BpmnError bpmnError=new BpmnError("error");
        }
    }
}
