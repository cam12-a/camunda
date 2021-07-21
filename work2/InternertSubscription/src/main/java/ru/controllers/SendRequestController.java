package ru.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.models.ClientData;
import ru.request.HttpRequest;
import ru.request.MyWebClient;

@Controller
@RestController

public class SendRequestController {
    private static final String url="https://randomuser.me/api/";
    private WebClient webClient= WebClient.create(url);
    MyWebClient myWebClient=new MyWebClient();
    ClientData clientData=new ClientData();
    HttpRequest httpRequest=new HttpRequest();
    @GetMapping(value = "/getClientData/")
    public Mono<ClientData> getClient() throws JsonProcessingException {
        String response=this.httpRequest.senHTTPRequest("https://randomuser.me/api/");
        this.httpRequest.setDataFromJsonToUserForm(response);

        myWebClient.sendRequest(url).subscribe(System.out::println);
        return myWebClient.sendRequest(url);
    }
}
