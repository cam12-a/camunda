package ru.chat.vault.server.Service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.chat.model.Credentials;
import ru.chat.model.USecret;

import javax.print.attribute.Attribute;

@Service
@Data
public class RequestData {

    private USecret credentials;
    public Mono<USecret> sendSecret(String url){
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(url)
                .body(Mono.just(credentials), USecret.class)
                .retrieve().bodyToMono(USecret.class);



    }

    public Mono<USecret> getRequest(String url){
        WebClient webClient=WebClient.create();
        return webClient.get()
                .uri(url)
                .header("X-Vault-Token","hvs.nyEsw8Dn363AAx3vQ7RwNWpE")
                .retrieve().bodyToMono(USecret.class);
    }
}
