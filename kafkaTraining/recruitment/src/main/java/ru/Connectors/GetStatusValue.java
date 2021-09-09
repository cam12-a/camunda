package ru.Connectors;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.Model.ApplicationData;

@Component
public class GetStatusValue {

    public ApplicationData sendStatus(String appNumber) {
        /*HttpConnector httpConnector= Connectors.getConnector(HttpConnector.ID);
        HttpRequest httpRequest=httpConnector.createRequest().get().url(URL);
        HttpResponse httpResponse=httpRequest.execute();*/
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri("http://localhost:7070/appData/"+appNumber+"/")
                .retrieve()
                .bodyToMono(ApplicationData.class)
                .block();
    }
}
