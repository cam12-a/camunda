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


    @PostMapping(value = "/addOperators/")
    public void addOperatorAssistant(@RequestBody OperatorAssistant operatorAssistant){

         saveEntityTemporary.setEntities(operatorAssistant.getOperatorAssistant());
    }

    @GetMapping(value = "/OperatorAssistantList/{operatorId}")
    public Map<String, String> getEntities(@PathVariable String operatorId) {

        Map<String, String> entities = new HashMap<>();
        List<Map<String, OperatorId>> opAssistant=saveEntityTemporary.getEntities();
        for(int i=0;i<opAssistant.size();i++){
            try{
                entities.put(operatorId,opAssistant.get(i).get(operatorId).getId_assistant());
            }catch (Exception e){
                 entities.put("error","данного оператора не существует");
                BpmnError bpmnError=new BpmnError("error");
            }
        }
        return entities;

    }

}
