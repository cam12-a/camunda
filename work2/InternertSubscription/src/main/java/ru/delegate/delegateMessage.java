package ru.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import ru.service.sendMessage;

public class delegateMessage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        sendMessage sendMsg=new sendMessage();
        delegateExecution.setVariable("messageWhileRejected",sendMsg.sendMsg());

    }
}
