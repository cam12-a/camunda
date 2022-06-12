package ru.maralays.mfa.controllers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.maralays.mfa.Entity.QRCode;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.Model.AuthModel;
import ru.maralays.mfa.Model.QRCodeModel;
import ru.maralays.mfa.Model.ResendToken;
import ru.maralays.mfa.Model.ResponseModel;
import ru.maralays.mfa.error.APIException;
import ru.maralays.mfa.security.TokenGenerator;
import ru.maralays.mfa.service.DAO.TokenTypeDAO;
import ru.maralays.mfa.service.DAO.UsersTokensDAO;
import ru.maralays.mfa.service.QRCODE.GenerateQRCode;
import ru.maralays.mfa.service.DAO.QRCodeDAO;
import ru.maralays.mfa.service.QRCODE.SendQRCode;
import ru.maralays.mfa.service.DAO.UsersDAO;
import ru.maralays.mfa.service.Tokens.TokenCheck;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/qrcode/")

@OpenAPIDefinition(info = @Info(title = "qrCode api", description = "this rest api generates and send end receive the QRCode", termsOfService = "http://localhost:8080/api/qrcode/", version = "1.0.1"))
public class QRCodeRestAPI {

    @Autowired
    private SendQRCode sendQRCode;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private GenerateQRCode generateQRCode;
    @Autowired
    QRCodeDAO qrCodeDAO;
    @Autowired
    APIException apiException;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    UsersTokensDAO usersTokensDAO;

    @Autowired
    TokenCheck tokenCheck;

    @Autowired
    TokenTypeDAO tokenTypeDAO;



    @PostMapping(value = "submit/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String verifyQRCode(@RequestBody QRCodeModel qrCodeModel){
        return qrCodeModel.toString();
    }

    @GetMapping(value = "generateqrcode/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResendToken> generateQrCode(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String message = request.getRequestURI()+username;
        Users users= usersDAO.findUsers(username);
        String token= request.getHeader("authorization");
        log.error("authorization "+token);
        TokenGenerator tokenGenerators=new TokenGenerator();
        Long expireTimeString=Long.parseLong(tokenGenerators.getClaimFromToken(token,"exp"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String expireTime= formatter.format(new Date(expireTimeString*1000));
        log.error("date expire date "+expireTime);
        TokenType tokenType=tokenTypeDAO.getTokenTypeByName("access_token");

       if(tokenCheck.checkToken(users,tokenGenerators,token,tokenType,"access_token")){
            tokenType=tokenTypeDAO.getTokenTypeByName("refresh_token");

            UsersTokens refresh_token=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(users,tokenType);
            tokenType=tokenTypeDAO.getTokenTypeByName("access_token");
            //token= tokenGenerators.generateToken(users,"access_token",tokenGenerator.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN());
            log.error("inside token refresh "+refresh_token);
            expireTimeString=Long.parseLong(tokenGenerators.getClaimFromToken(refresh_token.getToken(),"exp"));

            Long CurrentDate=new Date().getTime();
            if(new Date(expireTimeString*1000).getTime()<CurrentDate)
                return new ResponseEntity<>(new ResendToken("redirect",new ResponseModel("redirect",users)),HttpStatus.OK);

            //usersTokensDAO.saveWithInitialization(users,tokenTypeDAO.getTokenTypeByName("access_token"),tokenGenerators.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN(),token);
            if(tokenCheck.checkToken(users,tokenGenerators,refresh_token.getToken(),tokenType,"refresh_token")){
                tokenType=tokenTypeDAO.getTokenTypeByName("access_token");
                token= tokenGenerators.generateToken(users,"access_token",tokenGenerator.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN());
                usersTokensDAO.saveWithInitialization(users,tokenTypeDAO.getTokenTypeByName("access_token"),tokenGenerators.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN(),token);
                log.error("need new token"+token);

            }
            else
                return new ResponseEntity<>(new ResendToken("redirect",new ResponseModel("redirect",users)),HttpStatus.OK);
        }



        if(users!=null) {
            BufferedImage bi=generateQRCode.generateQRCode(generateQRCode.encryptedQRCode(message));
            QRCode qrCode=new QRCode();
            qrCode.setUsers(users);
            qrCode.setEncodeQRCode(generateQRCode.getEncodingQRText());
            formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            qrCode.setDateSend(formatter.format(new Date()));
           // qrCodeDAO.saveQRCode(qrCode);
            File outputfile = new File("qr_for_"+username+"_.png");
            ImageIO.write(bi, "png", outputfile);
            ByteArrayOutputStream outfile = new ByteArrayOutputStream();
            bi=ImageIO.read( new File("qr_for_"+username+"_.png"));
            ImageIO.write(bi,"png",outfile);
            byte[] bytes=outfile.toByteArray();
            log.info("error",bytes.toString());
            log.error("cookie ",request.getCookies());
            return new ResponseEntity<>(new ResendToken(token,
                    new ResponseModel(Base64.getEncoder().encodeToString(bytes),users)),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResendToken("",new ResponseModel("",null)),HttpStatus.OK);
    }

    @PostMapping(value="/receive_qrcode/",consumes = MediaType.APPLICATION_JSON_VALUE
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ReceiveQRCode(@RequestBody QRCode qrCode) throws Throwable {
        Users users=usersDAO.findUsers(qrCode.getUsers().getUsername());
        qrCode.setUsers(users);
        QRCode tempQrcode = qrCodeDAO.getLastInseredQRCode(users);
        tempQrcode.setUsers(qrCode.getUsers());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tempQrcode.setDateReceive(formatter.format(new Date()));
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
       // log.info("qr info"+tempQrcode.toString());
        if(tempQrcode.getEncodeQRCode().equals(qrCode.getEncodeQRCode())){
            qrCodeDAO.saveQRCode(tempQrcode);
            return new ResponseEntity<>("authorized", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Вы не имеете доступ к системе", HttpStatus.FORBIDDEN);


    }

}
