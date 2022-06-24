package ru.maralays.mfa.service.mobileIntegration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.maralays.mfa.Entity.MobileDetectionEntity;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Model.PushNotificationRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendRequest {

    private PushNotificationRequest pushModel;
    private Object users;

    public Mono<String> sendNotification(String url){
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(url)
                .header("authorization","key=AAAATFkcVmo:APA91bHgz-qS6APb4xFGWMpEXCF_kMjFNLtQZ9rpON2wNyPAbYCbm6YalNLRBhySQEGvOV1GpEsynFcfnZiVJvPLGg_4cjKY7kCcbVIRLUheb3cQu_LYABbBlyKPYXtgjI3i6bcFiFJV")
                .body(Mono.just(pushModel), PushNotificationRequest.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Object> postRequest(String url){
        WebClient webClient=WebClient.create();
        return webClient.post()
                .uri(url)
                .body(Mono.just(users),Object.class)
                .retrieve()
                .bodyToMono(Object.class);
    }





}
