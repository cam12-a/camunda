package ru.Services;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

@Service
public class APIForRunningProcess {

    public String runProcessTeamA(String processKey)
    {
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService= processEngine.getRuntimeService();
        return runtimeService.startProcessInstanceByKey(processKey).getId();
    }


}
