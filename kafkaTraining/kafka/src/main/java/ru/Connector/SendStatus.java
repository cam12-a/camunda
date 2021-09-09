package ru.Connector;

import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.connect.spi.Connector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.Model.ApplicationData;


@Component
public class SendStatus {

    public ApplicationData sendStatus(String url) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ApplicationData.class)
                .block();
    }

}
