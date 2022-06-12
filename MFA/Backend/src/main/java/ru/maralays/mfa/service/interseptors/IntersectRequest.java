package ru.maralays.mfa.service.interseptors;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.Model.AuthModel;
import ru.maralays.mfa.Model.ResponseModel;
import ru.maralays.mfa.security.TokenGenerator;
import ru.maralays.mfa.service.DAO.TokenTypeDAO;
import ru.maralays.mfa.service.DAO.UsersDAO;
import ru.maralays.mfa.service.DAO.UsersTokensDAO;
import ru.maralays.mfa.service.Tokens.TokenCheck;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;


@Slf4j
@NoArgsConstructor
@Component
public class IntersectRequest  implements  HandlerInterceptor {

    @Autowired
    UsersTokensDAO usersTokensDAO;
    @Autowired
    UsersDAO usersDAO;
    @Autowired
    TokenTypeDAO tokenTypeDAO;
    @Autowired
    TokenCheck tokenCheck;
    @Autowired
    TokenGenerator tokenGenerator;


    private String username;
    private String password;
    private final Logger logger = LoggerFactory.getLogger(IntersectRequest.class);

    private String refresh_token;

    public String getRefresh_token() {
        return refresh_token;
    }




    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preprepre");

       Enumeration param=request.getParameterNames();
       while(param.hasMoreElements()){
           String nextElement=param.nextElement().toString();
           if(nextElement.equals("username"))
                username=request.getParameter(nextElement);
           if(nextElement.equals("password"))
               password=request.getParameter(nextElement);
           log.info("preHandle username "+username+ " password "+password);

           //log.error("preHandle "+  nextElement+ request.getParameter(nextElement));
       }
        /*InputStream a = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(a));
        String line = null;
        while((line = in.readLine()) != null) {
            System.out.println(line);

        }*/






       /*
        if(username!=null && password!=null) {
            log.error("eeee not null "+username+" "+password);
            try {
                Users users = usersDAO.userByUsernameAndPassword(username, password);
                log.error("pre user info" + users.toString());
                TokenType tokenType = tokenTypeDAO.getTokenTypeByName("access_token");
                UsersTokens usersTokens = usersTokensDAO.findByUserTokenAndTokenTypeOrderById(users, tokenType);
                log.error("pre token" + usersTokens);
                Long expireTimeString = Long.parseLong(tokenGenerator.getClaimFromToken(usersTokens.getToken(), "exp"));
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String expireTime = formatter.format(new Date(expireTimeString * 1000));
                if (new Date(expireTimeString * 1000).getTime() < new Date().getTime()) {
                    log.error("user agent " + request.getHeader("user-agent"));
                    log.error("ip " + request.getRemoteAddr());
                    response.getWriter().write("You are not authorized");
                    return false;
                }


            } catch (Throwable e) {
                log.error("pre error");
                e.printStackTrace();
            }
        }
            */



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("a");
        log.info("postHandle authorization "+request.getHeader("Authorization"));


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*
        if(response.getStatus()==200) {
            try {
                log.info("In username " + username + " password " + password);

                Users users = usersDAO.userByUsernameAndPassword(username, password);
                /*TokenType tokenType=tokenTypeDAO.getTokenTypeByName("refresh_token");
                List<UsersTokens> allTokens=usersTokensDAO.getLastToken(users,tokenType);
                refresh_token=allTokens.get(allTokens.size()-1).getToken();

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        */


        log.error("afterCompletion");


    }
}
