package ru.chat.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.chat.model.AuthToken;
import ru.chat.model.Credentials;
import ru.chat.model.USecret;
import ru.chat.security.GenerateKeys;
import ru.chat.vault.server.Service.RequestData;
import ru.chat.vault.server.Service.UserSecrets;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/vault/api/secret/")
@OpenAPIDefinition(info=@Info(title = "Secrets api",description = "secret manager API",termsOfService = "http:127.0.0.1:8200",version = "1.0"))

@Slf4j
public class VaultAPI {
    @Autowired
    RequestData requestData;

    @Autowired
    VaultTemplate vaultTemplate;

    @PostMapping(value = "create/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<String>> createSecret(@RequestBody USecret credentials){
        USecret data=credentials;
        /*GenerateKeys generateKeys=new GenerateKeys();
        KeyPair keyPair= generateKeys.generateKeys();
        data.getData().setPrivateKey(keyPair.getPrivate().toString());
        data.getData().setPublicKey(keyPair.getPublic().toString());

         */
        vaultTemplate.write("chatkv/data/chat/users/"+data.getData().getUsername(),data=credentials);
        return null;

    }

    @GetMapping(value = "user/{username}" ,produces = MediaType.APPLICATION_JSON_VALUE, headers = {"X-Vault-Token=hvs.nyEsw8Dn363AAx3vQ7RwNWpE"})
    public ResponseEntity<Map<String, Object>> getCredential(@PathVariable String username, HttpServletRequest request){

        USecret uSecret=new USecret();

        Map<String, Object> response=new HashMap<String, Object>();
        //log.error("as "+requestData.sendSecret("http://127.0.0.1:8200/v1/chatkv/data/chat").subscribe(r -> System.out.println(r.toString())));

        vaultTemplate.read("chatkv/data/chat/users/"+username).getData().forEach((k,v)-> response.put(k,v));
        //AuthToken authToken=new AuthToken();
        //authToken.setRoot_token("hvs.SO74pvqetSLlAHzh5MOmhPjr");

       // log.error("as "+requestData.getRequest("http://127.0.0.1:8088/vault/api/secret/user/"+username).subscribe(r -> System.out.println(r.toString())));

       // log.error("parsed data "+uSecret.getData());

        return new ResponseEntity<>( response,HttpStatus.OK);
        //return null;
    }



}
