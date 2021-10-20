package ru.Services;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.models.Notifications;

import java.util.Map;
import java.util.logging.Logger;


@Service("CallExternalService")
public class CallExternalService {

    public Object executeExternalService(String url, Object classToParse){
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(classToParse.getClass())
                .block();

    }
    public Object executeExternalServicePostMethod(String url, Notifications notifications){
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(url)
                .retrieve()
                .bodyToMono(notifications.getClass())
                .block();

    }







}
