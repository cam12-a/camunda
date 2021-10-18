package ru.Services;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.logging.Logger;


@Service("CallExternalService")
public class CallExternalService {

    private final Logger logger = Logger.getLogger(CallExternalService.class.getName());
    public void externalTask(String topic){
        System.out.println("okok");

        ExternalTaskClient client= ExternalTaskClient.create()
                .baseUrl("http://localhost:8085/addOperators/")
                .asyncResponseTimeout(10000)
                .build();

       client.subscribe(topic)
               .lockDuration(1000)
               .handler((externalTask, externalTaskService) -> {
                   logger.info("назначение руководителю и еще помощнику если он имеется");
                   try {

                       WebClient webClient = WebClient.create();
                       String a= webClient.get()
                               .uri("http://localhost:8085/OperatorAssistantList/"+"operatorId"+"/")
                                .retrieve()
                                .bodyToMono(String.class)
                               .block();

                       System.out.println(a);

                   }catch (Exception ex){
                        System.out.println(ex.getMessage());
                   }

               }).open();

       externalTask("manager-assistant");
    }







}
