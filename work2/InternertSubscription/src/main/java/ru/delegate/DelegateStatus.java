package ru.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import ru.models.AboutProcesses;
import ru.service.Status;

public class DelegateStatus implements JavaDelegate {


    private AboutProcesses aboutProcesses=new AboutProcesses();

    private Status status=new Status();
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        if(this.status.setStatus(delegateExecution.getVariable("status").toString()).equals("cancelled")){
            aboutProcesses.setStatusSetBySystem("cancelled");

            delegateExecution.setVariable("statusSetBySystem", aboutProcesses.getStatusSetBySystem());
        }

        if(this.status.setStatus(delegateExecution.getVariable("status").toString()).equals("called_back")){
            aboutProcesses.setStatusSetByClient("called_back");
            delegateExecution.setVariable("statusSetByClient",aboutProcesses.getStatusSetByClient());
        }
        if(this.status.setStatus(delegateExecution.getVariable("status").toString()).equals("called_back")){
            aboutProcesses.setStatusSetByManager("approved");
            delegateExecution.setVariable("statusSetByManager",aboutProcesses.getStatusSetByManager());
        }


    }
}
