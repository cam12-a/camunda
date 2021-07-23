package ru.Services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.models.Person;


@Service
public class GetPerson  extends Person {
    protected static final String  url="https://randomuser.me/api";
    WebClient webClient = WebClient.create();

    private Person person;

    public Person GetPerson(){
        return this.person;
    }
    public String PersonInfo()
    {

         return webClient.get()
                .uri(url)
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();

    }

    public  void ParsePersonData(String response) throws JsonProcessingException
    {

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(response);
        jsonNode=jsonNode.get("results");

        for (JsonNode jsonNode1 : jsonNode) {
            this.setGender(jsonNode1.get("gender").toString().substring(1,jsonNode1.get("gender").toString().length()-1));
            this.setFirstname(jsonNode1.get("name").get("first").toString().substring(1,jsonNode1.get("name").get("first").toString().length()-1));
            this.setLastname(jsonNode1.get("name").get("last").toString().substring(1,jsonNode1.get("name").get("last").toString().length()-1));
        }


    }

}
