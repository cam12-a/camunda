package ru.maralays.mfa.service.mobileIntegration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.maralays.mfa.Entity.MobileDetectionEntity;
import ru.maralays.mfa.Model.PushNotificationRequest;

@Data
@NoArgsConstructor
public class SendRequest {

    private PushNotificationRequest pushModel;

    public Mono<String> sendNotification(String url){
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(url)
                .header("authorization","key=AAAATFkcVmo:APA91bHgz-qS6APb4xFGWMpEXCF_kMjFNLtQZ9rpON2wNyPAbYCbm6YalNLRBhySQEGvOV1GpEsynFcfnZiVJvPLGg_4cjKY7kCcbVIRLUheb3cQu_LYABbBlyKPYXtgjI3i6bcFiFJV")
                .body(Mono.just(pushModel), PushNotificationRequest.class)
                .retrieve()
                .bodyToMono(String.class);
    }


    public Mono<MobileDetectionEntity> getRequest(String url){
        WebClient webClient=WebClient.create();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/mobile/detect/")
                        .queryParam("normal",true)
                        .queryParam("devicePlatform", "IOS")
                        .queryParam("mobile",true)
                        .queryParam("tablet",true)
                        .port(8085)
                        .host("172.17.158.45")
                        .scheme("http")
                        .build())
                .retrieve().bodyToMono(MobileDetectionEntity.class);
    }



}
