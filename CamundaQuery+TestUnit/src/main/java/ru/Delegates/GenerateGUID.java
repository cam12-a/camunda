package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.Modules.ApplicationData;
import ru.Services.ProcessInfo;

import javax.inject.Inject;
import javax.inject.Named;

@Named("GenerateGUID")
public class GenerateGUID implements JavaDelegate  {

    @Inject
    private GenerateGUID generateGUID;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ProcessInfo processInfo=new ProcessInfo(delegateExecution.getProcessEngine().getName());
        ApplicationData applicationData=new ApplicationData();
        int amountProcess=processInfo.getAmountProcess(processInfo.getUserID(),"RequestVacationSchedule")+1;
        int allProcess=processInfo.getAllFinishedProcessInstances(processInfo.getUserID(),"RequestVacationSchedule").size();
        applicationData.setApplicationGUI("APP"+amountProcess);
        delegateExecution.setVariable("applicationGUI",applicationData.getApplicationGUI());
        System.out.println(applicationData.getApplicationGUI());
        System.out.println(processInfo.getUserID());
    }
}
