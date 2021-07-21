package ru.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.models.ClientData;

public class MyWebClient {

    WebClient webClient= WebClient.builder().build();
    public Mono<ClientData> sendRequest(String url){
        return webClient.get()
               .uri(url)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(ClientData.class));
    }
}
