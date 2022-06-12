package ru.chat.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chat.DAO.MessageDAO;
import ru.chat.DAO.UsersDAO;
import ru.chat.entity.Messages;
import ru.chat.entity.Users;
import ru.chat.model.Credentials;
import ru.chat.model.USecret;
import ru.chat.security.CreateEndToEndTunnel;
import ru.chat.security.EncryptDecryptMessage;
import ru.chat.security.GenerateKeys;
import ru.chat.vault.server.Service.RequestData;
import ru.chat.vault.server.Service.UserSecrets;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/message/")
@Slf4j
@OpenAPIDefinition(info=@Info(title = "message api",description = "this is an api that manages message processing",termsOfService = "http://172.17.137.45:8088/api/message/",version = "1.0"))
public class Message {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    UsersDAO usersDAO;
    @Autowired
    UserSecrets userSecrets;

    @PostMapping(value = "send_message/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Messages> sendMessage(@RequestBody Messages messages, HttpServletRequest request) throws GeneralSecurityException {

        GenerateKeys endPoint1=new GenerateKeys();

        GenerateKeys endPoint2=new GenerateKeys();

        PrivateKey endPoint1PrivateKey;
        PrivateKey endPoint2PrivateKey;

        PublicKey endPoint1PublicKey;
        PublicKey endPoint2PublicKey;

        USecret uSecret;
        RequestData requestData=new RequestData();

        uSecret= requestData.getRequest("http://127.0.0.1:8088/vault/api/secret/user/"+messages.getMessageSenderUsers().getUsername()).block();

        endPoint1PublicKey=endPoint1.loadPublicKey(uSecret.getData().getPublicKey());
        endPoint1PrivateKey=endPoint1.loadPrivateKey(uSecret.getData().getPrivateKey());

        uSecret= requestData.getRequest("http://127.0.0.1:8088/vault/api/secret/user/"+messages.getMessageReceiverUsers().getUsername()).block();

        endPoint2PrivateKey=endPoint2.loadPrivateKey(uSecret.getData().getPrivateKey());
        endPoint2PublicKey=endPoint2.loadPublicKey(uSecret.getData().getPublicKey());



        CreateEndToEndTunnel createEndToEndTunnel1=new CreateEndToEndTunnel(messages.getMessageSenderUsers().getUsername() ,messages.getMessageReceiverUsers().getUsername());

        createEndToEndTunnel1.createTunnel(endPoint1PrivateKey,endPoint2PublicKey);

        CreateEndToEndTunnel createEndToEndTunnel2=new CreateEndToEndTunnel(messages.getMessageSenderUsers().getUsername() ,messages.getMessageReceiverUsers().getUsername());
        createEndToEndTunnel2.createTunnel(endPoint2PrivateKey,endPoint1PublicKey);


        Map<String,String> exchangedKey=new HashMap<>();


        if (uSecret.getData().getUserTunnel().size() != 0) {
            exchangedKey = uSecret.getData().getUserTunnel();
        }
        exchangedKey.put(messages.getMessageReceiverUsers().getUsername(),Arrays.toString(createEndToEndTunnel1.getSharedSecret()));
        uSecret.getData().setUserTunnel(exchangedKey);

        requestData.setCredentials(uSecret);

        log.error("as "+requestData.sendSecret("http://127.0.0.1:8088/vault/api/secret/create/").subscribe(r -> System.out.println(r.toString())));


        log.error("user data "+uSecret.toString());


        log.error("key exchange 1 "+ Arrays.toString(createEndToEndTunnel1.getSharedSecret()));
        log.error("endPoint1Key "+" public key "+endPoint1PublicKey+" private key "+endPoint1.getPrivateKeyAsHex(endPoint1PrivateKey));

        EncryptDecryptMessage encryptDecryptMessage=new EncryptDecryptMessage();
        String encryptedMsg=encryptDecryptMessage.encrypt(messages.getMessageText(),createEndToEndTunnel2.generateKey(),"AES");


        log.error("key exchange 2 "+Arrays.toString(createEndToEndTunnel2.getSharedSecret()));
        log.error("endPoint2Key "+" public key "+endPoint2PublicKey+" private key "+endPoint2.getPrivateKeyAsHex(endPoint2PrivateKey));


        log.error("encrypted message "+encryptedMsg);
        log.error("decrypted message "+encryptDecryptMessage.decrypt(encryptedMsg,createEndToEndTunnel1.generateKey(),"AES"));






        setData(messages);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        messages.setDateSend(formatter.format(new Date()));
        return new ResponseEntity<>(messageDAO.createMessages(messages) , HttpStatus.OK);
    }

    @PutMapping(value = "edit_message/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Messages> editMessage(@RequestBody Messages messages, HttpServletRequest request, HttpServletResponse response){
        setData(messages);

        return new ResponseEntity<>( messageDAO.editMessages(messages), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete_message/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMessage(@RequestBody Messages messages, HttpServletRequest request, HttpServletResponse response) {
        setData(messages);
        Messages tempMessages = messageDAO.getMessageByDateSendUserSenderAndUserReceiver(messages);
        log.error("delete message "+tempMessages);
        if(tempMessages!=null){
            messageDAO.deleteMessages(tempMessages);
            return new ResponseEntity<>("message has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("message doesn't exist", HttpStatus.OK);


    }

    @GetMapping(value = "receive/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Messages>> receivedMessage(@PathVariable String username){
        GenerateKeys keysGenerator=new GenerateKeys();
        String algorithm = "AES";
        String path = System.getProperty("user.dir");
        PrivateKey privateKey = keysGenerator.loadPrivateKey(path, algorithm);

        EncryptDecryptMessage encryptDecryptMessage=new EncryptDecryptMessage();
        List<Messages> tempMessages=messageDAO.getReceiveMessage(username);



        return new ResponseEntity<>( tempMessages , HttpStatus.OK);
    }

    public void setData(Messages messages){
        Users userReceive=usersDAO.findByUsername(messages.getMessageReceiverUsers().getUsername());
        Users userSender=usersDAO.findByUsername(messages.getMessageSenderUsers().getUsername());
        messages.setMessageSenderUsers(userSender);
        messages.setMessageReceiverUsers(userReceive);
    }

}
