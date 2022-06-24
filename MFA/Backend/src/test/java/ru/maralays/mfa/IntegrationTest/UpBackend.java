package ru.maralays.mfa.IntegrationTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.maralays.mfa.MfaApplication;
import ru.maralays.mfa.service.mobileIntegration.SendRequest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@ContextConfiguration(classes = {MfaApplication.class})
public class UpBackend {


    SendRequest sendRequest = new SendRequest();
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void  backendIsUp(){
        Map<String, String> serverState=new LinkedHashMap<>();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();




        serverState =(LinkedHashMap) getRequest("/actuator/health",8085,"localhost","http").block();
        log.info("beforeEach");
        Map<String, String> finalServerState = serverState;
        assertTrue(finalServerState.get("status")!="UP","server is down");

    }

    public Mono<Object> getRequest(String path, int port, String hostname, String scheme){
        WebClient webClient=WebClient.create();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .port(port)
                        .host(hostname)
                        .scheme(scheme)
                        .build())
                .retrieve().bodyToMono(Object.class);
    }
}
