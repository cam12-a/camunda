package ru.Controllers;


import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.models.OperatorAssistant;
import ru.models.OperatorId;
import ru.Services.SaveEntityTemporary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OperatorAssistantEntity {
    @Autowired
    OperatorAssistant operatorAssistant;
    @Autowired
    SaveEntityTemporary saveEntityTemporary;
    @Autowired
    OperatorId operatorId;


    @PostMapping(value = "/api/assistant-service/operator-list/")
    public void addOperatorAssistantList(@RequestBody OperatorAssistant operatorAssistant){

         saveEntityTemporary.setEntities(operatorAssistant.getOperatorAssistant());
    }

    @PostMapping(value = "/api/assistant-service/operator-list/{userId}/{assistantId}/")
    public void addOperatorAssistant(@PathVariable String userId, @PathVariable String assistantId){

        Map<String, String> tempMap= new HashMap<>();
        tempMap.put(userId,assistantId);
        saveEntityTemporary.setEntity(tempMap);
    }

    @GetMapping(value = "/api/assistant-service/operator-assistant-list/{operatorId}/")
    public Map<String, String> getEntities(@PathVariable String operatorId) {

        Map<String, String> entities = new HashMap<>();
        List<Map<String, OperatorId>> opAssistant=saveEntityTemporary.getEntities();
        for(int i=0;i<opAssistant.size();i++){
            try{
                entities.put(operatorId,opAssistant.get(i).get(operatorId).getAssistantId());
            }catch (Exception e){
                 entities.put("error","данного оператора не существует");
                BpmnError bpmnError=new BpmnError("error");
            }
        }
        return entities;

    }

}
