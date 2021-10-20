package ru.Delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import ru.Services.CallExternalService;
import ru.models.ApplicationData;

import java.util.HashMap;
import java.util.Map;

public class NotificationsLoggerService implements JavaDelegate {

    @Autowired
    CallExternalService callExternalService;
    @Autowired
    ApplicationData applicationData;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


    }
}
