package ru.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.models.ClientData;
import ru.request.HttpRequest;
import ru.request.MyWebClient;


public class CamundaStartService implements JavaDelegate {

    HttpRequest httpRequest=new HttpRequest();
    private static final String url="https://randomuser.me/api/";
   private WebClient webClient= WebClient.create(url);
   MyWebClient myWebClient=new MyWebClient();
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        myWebClient.sendRequest(url);
       // String response=this.httpRequest.senHTTPRequest("https://randomuser.me/api/");
        //this.httpRequest.setDataFromJsonToUserForm(response);
        //myWebClient.sendRequest(url);
       //System.out.println( myWebClient.sendRequest(url));
       /* RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity("https://randomuser.me/api/", String.class);
        this.httpRequest.setDataFromJsonToUserForm(response.getBody());*/
        delegateExecution.setVariable("first_name",httpRequest.getFirst_name());
        delegateExecution.setVariable("last_name",httpRequest.getLast_name());

        delegateExecution.setVariable("country",httpRequest.getCountry());
        
    }


}
