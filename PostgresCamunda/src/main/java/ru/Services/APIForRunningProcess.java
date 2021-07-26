package ru.Services;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("APIForRunningProcess")
public class APIForRunningProcess {

    public void runProcesses(String processKey, Map<String,String> variables)
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String id=runtimeService.startProcessInstanceByKey(processKey).getId();
        runtimeService.setVariables(id,variables);
       // System.out.println(id);
    }


}
