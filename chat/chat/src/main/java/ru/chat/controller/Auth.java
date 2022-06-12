package ru.chat.controller;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chat.DAO.UsersDAO;
import ru.chat.entity.Users;
import ru.chat.model.Credentials;
import ru.chat.model.PrivateKeyModel;
import ru.chat.model.PublicKeyModel;
import ru.chat.model.USecret;
import ru.chat.security.EncryptDecryptMessage;
import ru.chat.security.GenerateKeys;
import ru.chat.vault.server.Service.RequestData;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPublicKey;

@RestController
@RequestMapping(value = "/api/auth/")
@Slf4j
@OpenAPIDefinition(info=@Info(title = "auth api",description = "this is an api that provide auth processing for an user",termsOfService = "http://172.17.137.45:8088/api/auth/",version = "1.0"))

public class Auth {

    @Autowired
    UsersDAO usersDAO;


    @PostMapping(value = "/login/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> login(@RequestBody Users user, HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException {

        log.error("login "+user.toString());
        GenerateKeys keysGenerator=new GenerateKeys();

        RequestData requestData=new RequestData();

        //String path = System.getProperty("user.dir");
        KeyPair keyPair=keysGenerator.generateKeys();

        USecret uSecret=new USecret();

        Credentials credentials=new Credentials();
        credentials.setUsername(user.getUsername());

        PublicKeyModel publicKeyModel=new PublicKeyModel();
        PrivateKeyModel privateKeyModel=new PrivateKeyModel();
        PublicKey publicKey=keyPair.getPublic();
        publicKeyModel.setAlgorithm(publicKey.getAlgorithm());
        publicKeyModel.setEncoded(publicKey.getEncoded());
        publicKeyModel.setFormat(publicKey.getFormat());


        PrivateKey privateKey=keyPair.getPrivate();

        privateKeyModel.setAlgorithm(privateKey.getAlgorithm());
        privateKeyModel.setEncoded(privateKey.getEncoded());
        privateKeyModel.setFormat(privateKey.getFormat());

        credentials.setPublicKey(keysGenerator.savePublicKey(publicKey));

        credentials.setPrivateKey(keysGenerator.savePrivateKey(keyPair.getPrivate()));

        //credentials.setPublicKey(keyPair.getPublic());

        log.error("publicKey "+publicKey.toString());

        uSecret.setData(credentials);

        Users getLogged=usersDAO.findUsers(user.getUsername(),user.getPassword());
        if(getLogged!=null){

            requestData.setCredentials(uSecret);
            log.error("credentials "+uSecret.toString());
            log.error("as "+requestData.sendSecret("http://127.0.0.1:8088/vault/api/secret/create/").subscribe(r -> System.out.println(r.toString())));
        }

       // log.error("as "+requestData.getRequest("http://127.0.0.1:8088/vault/api/secret/user/"+user.getUsername()).subscribe(r -> System.out.println(r.toString())));

       //log.error("parsed data "+uSecret.getData());

      // PublicKey pubK=keysGenerator.loadPublicKey(uSecret.getData().getPublicKey());
      // log.error("is true "+ pubK.toString());


        //keysGenerator.saveKeysToFiles(path,keyPair.getPublic(), keyPair.getPrivate());


        return new ResponseEntity<>(getLogged, HttpStatus.OK);

    }

    @PostMapping(value = "logout/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> logout(@RequestBody Users user, HttpServletRequest request, HttpServletResponse response) {



        return new ResponseEntity<>(new Users(), HttpStatus.OK);
    }
}
