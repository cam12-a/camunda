package ru.maralays.mfa.controllers;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maralays.mfa.Model.Note;
import ru.maralays.mfa.Model.PushNotificationRequest;
import ru.maralays.mfa.Model.PushNotificationResponse;
import ru.maralays.mfa.service.mobileIntegration.FirebaseMessagingService;
import ru.maralays.mfa.service.mobileIntegration.PushNotificationService;

import java.io.IOException;


@RestController
@RequestMapping("/api/notification/")
@OpenAPIDefinition(info = @Info(title = "Notification service", description = "send notification service to users", termsOfService = "http://localhost:8089/api/notification/", version = "1.0.1"))

public class PushNotificationController {

    private PushNotificationService pushNotificationService;


    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        System.out.println("princr");
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }



}
