package ru.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.Services.APIForRunningProcess;
import ru.Services.CountMenAndWomen;
import ru.Services.GetPerson;
import ru.parse.Mapping;
import ru.parse.Results;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        Mapping  mapping= getPerson.PersonInfo();

       System.out.println(mapping.getResults()[0].getName().first);
       getPerson.setFirst(mapping.getResults()[0].getName().first);
       getPerson.setLast(mapping.getResults()[0].getName().last);
       getPerson.setGender(mapping.getResults()[0].getGender());

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("Firstname",  getPerson.getFirst());
        variables.put("Lastname", getPerson.getLast());
        variables.put("Gender", getPerson.getGender());

        return variables;
    }
    @GetMapping(value = "/camunda/rest-api/calc")
    public String GetAmountMenWomen(@RequestParam("team") String team)
    {
        if( team.equals("TeamA") || team.equals("TeamB")) {
            CountMenAndWomen countMenAndWomen = new CountMenAndWomen();
            return countMenAndWomen.MenAndWomenAmount(team);
        }
        else
            return "Неправильный ввод, доступны следующие значение TeamA и TeamB";
    }


}
