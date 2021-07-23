package ru.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.Services.APIForRunningProcess;
import ru.Services.CountMenAndWomen;
import ru.Services.GetPerson;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RunProcess {

    @PostMapping(value = "/camunda/rest-api/process/{id}")
    public void GenerateProcess(@PathVariable("id") int id) throws JsonProcessingException {
        for(int i=0;i<id;i++) {
            APIForRunningProcess TeamA = new APIForRunningProcess();
            TeamA.runProcesses("TeamA", setVariables());
            APIForRunningProcess TeamB = new APIForRunningProcess();
            TeamB.runProcesses("TeamB", setVariables());

        }
    }


    public static Map<String, String> setVariables() throws JsonProcessingException {
        GetPerson getPerson = new GetPerson();
        String Response = getPerson.PersonInfo();
        getPerson.ParsePersonData(Response);
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("Firstname", getPerson.getFirstname());
        variables.put("Lastname", getPerson.getLastname());
        variables.put("Gender", getPerson.getGender());
        System.out.println(getPerson.toString());
        return variables;
    }

    @GetMapping(value = "/camunda/rest-api/calc")
    public String GetAmountMenWomen(@RequestParam("team") String team)
    {
        CountMenAndWomen countMenAndWomen=new CountMenAndWomen();
        return countMenAndWomen.MenAndWomenAmount(team);
    }

}
