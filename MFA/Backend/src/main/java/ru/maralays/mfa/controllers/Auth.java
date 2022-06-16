package ru.maralays.mfa.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;
import ru.maralays.mfa.Entity.MobileDetectionEntity;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.Model.*;
import ru.maralays.mfa.error.APIException;
import ru.maralays.mfa.repository.UsersRepository;
import ru.maralays.mfa.security.TokenGenerator;
import ru.maralays.mfa.service.DAO.TokenTypeDAO;
import ru.maralays.mfa.service.DAO.UsersDAO;
import ru.maralays.mfa.service.DAO.UsersTokensDAO;
import ru.maralays.mfa.service.Tokens.TokenCheck;
import ru.maralays.mfa.service.mobileIntegration.SendRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/auth/")
@Slf4j
public class Auth {


    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    UsersTokensDAO usersTokensDAO;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    APIException apiException;

    @Autowired
    TokenTypeDAO tokenTypeDAO;
    @Autowired
    TokenCheck tokenCheck;


    @PostMapping(value = "/login/",consumes= {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<AuthModel> singIn(@RequestBody Users users, HttpServletResponse responseHeaders, HttpServletRequest request) throws Throwable {

        TokenGenerator tokenGenerator=new TokenGenerator();
        Users user=usersDAO.userByUsernameAndPassword(users.getUsername(),users.getPassword());
        String url="";

        log.error("msg "+request.getHeader("user-agent"));


        if(user!=null){
            UsersTokens usersTokens=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(user,tokenTypeDAO.getTokenTypeByName("access_token"));

            Long time_expire=tokenGenerator.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN();

            String access_token=tokenGenerator.generateToken(user,"access_token",time_expire);
            log.error("token in not verified "+users.toString());
            if(request.getHeader("user-agent").contains("Android") ||request.getHeader("user-agent").contains("IOS")){

                if( users.getIdFirebaseMessagingCloud()!=null) {
                    if (!usersTokens.getIdFirebaseMessagingCloud().equals(users.getIdFirebaseMessagingCloud())) {
                        SendRequest sendPushNotification = new SendRequest();
                        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();

                        pushNotificationRequest.setTitle("Новая угроза");
                        pushNotificationRequest.setMessage("Пользователь с ip " + request.getRemoteAddr() + " пытается получить доступ к вашей учеткой");
                        pushNotificationRequest.setToken(usersTokens.getIdFirebaseMessagingCloud());
                        pushNotificationRequest.setTopic("als");
                        sendPushNotification.setPushModel(pushNotificationRequest);
                        url = "http://172.17.158.45:8085/api/notification/token";
                        log.error("send push " + sendPushNotification.sendNotification(url).subscribe(r -> System.out.println(r.toString())));
                    }
                }


               if(userHaveSessionOpenOnMobile(user,"refresh_token")){
                    return new ResponseEntity<>(new AuthModel(new ResponseModel("you have an opened connection",user),access_token), HttpStatus.OK);
                }


            }
            else{
                users.setIdFirebaseMessagingCloud("");
            }

            usersTokensDAO.saveWithInitialization(user,tokenTypeDAO.getTokenTypeByName("access_token"),tokenGenerator.getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN(),access_token,users.getIdFirebaseMessagingCloud());

            tokenGenerator=new TokenGenerator();

            String refresh_token=tokenGenerator.generateToken(user,"refresh_token",tokenGenerator.getDEFAULT_EXPIRATION_TIME_REFRESH_TOKEN());

           usersTokensDAO.saveWithInitialization(user,tokenTypeDAO.getTokenTypeByName("refresh_token"),tokenGenerator.getDEFAULT_EXPIRATION_TIME_REFRESH_TOKEN(),refresh_token,users.getIdFirebaseMessagingCloud());

            Cookie cookie= new Cookie("refresh_token",refresh_token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(-1);
            responseHeaders.addCookie(cookie);

            return new ResponseEntity<>(new AuthModel(new ResponseModel("login success",user),access_token), HttpStatus.OK);

        }
        else
            return  new ResponseEntity<>(new AuthModel(new ResponseModel("credential invalid",null),null),HttpStatus.OK);
    }


    @GetMapping(value = "/logOut/{username}", consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResendToken> getToken(@PathVariable String username){
        log.error("use "+username);
        return new ResponseEntity<>(new ResendToken("redirect",new ResponseModel("redirect",null)),HttpStatus.OK);

    }


    @PostMapping(value = "/logout/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(@RequestBody UsersTokens usersTokens){

        String access_token=usersTokens.getToken();
        log.error("logout access_token "+access_token);
        TokenGenerator tokenGenerator= new TokenGenerator();
        String username=tokenGenerator.getClaimFromToken(access_token,"username");
        log.error("logout  username "+username);
        Users user=usersDAO.findUsers(username.replace("\"", ""));

        log.error("logout "+user+ " username "+username);
        UsersTokens usersTokens1=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(user,tokenTypeDAO.getTokenTypeByName("refresh_token"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        usersTokens1.setExpireTime(formatter.format(new Date()));
        if(usersTokensDAO.saveTokens(usersTokens1)!=null){
            usersTokens1=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(user,tokenTypeDAO.getTokenTypeByName("access_token"));
            formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            usersTokens1.setExpireTime(formatter.format(new Date()));
            usersTokensDAO.saveTokens(usersTokens1);

            return new ResponseEntity<>("logout successful",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("error while doing logout",HttpStatus.OK);

    }


    public Boolean userHaveSessionOpenOnMobile(Users users, String tokenTypes) throws ParseException {
        TokenGenerator tokenGenerator=new TokenGenerator();
        TokenType tokenType=tokenTypeDAO.getTokenTypeByName(tokenTypes);
        UsersTokens token=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(users,tokenType);
        log.info("token info "+token.getToken()+" date "+token.getExpireTime());
        Long expireTimeString=Long.parseLong(tokenGenerator.getClaimFromToken(token.getToken(),"exp"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if(token!=null){
            if(new Date(expireTimeString*1000).getTime()>=new Date().getTime()){
              return true;
            }
        }
       return false;
    }


}
