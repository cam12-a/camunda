package ru.maralays.mfa.service.mobileIntegration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.maralays.mfa.Model.PushModel;
import ru.maralays.mfa.Model.PushNotificationRequest;
import ru.maralays.mfa.Model.PushNotificationResponse;

@Data
@NoArgsConstructor
public class SendPushNotification {

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

}
