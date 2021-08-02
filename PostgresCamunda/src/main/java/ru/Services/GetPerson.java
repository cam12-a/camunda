package ru.Services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.models.Person;
import ru.parse.Mapping;
import ru.parse.Results;

import static org.camunda.spin.Spin.JSON;
import static org.camunda.spin.Spin.S;


@Service
public class GetPerson  extends Person {
    protected static final String  url= System.getenv("URLTOAPI");
    WebClient webClient = WebClient.create();
    private Person person;

    public Person GetPerson(){
        return this.person;
    }
    public Mapping PersonInfo()
    {
        //Mapping mapping=new Mapping();
         return webClient.get()
                .uri(url)
                 .retrieve()
                 .bodyToMono(Mapping.class)
                .block();

    }

    public String PersonInfoStr()
    {

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public  void ParsePersonData(String response) throws JsonProcessingException
    {
        Mapping person=JSON(response).mapTo(Mapping.class);

    }

}
