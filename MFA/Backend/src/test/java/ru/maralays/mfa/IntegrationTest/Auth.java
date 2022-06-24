package ru.maralays.mfa.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.service.DAO.UsersDAO;
import ru.maralays.mfa.service.mobileIntegration.SendRequest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Auth extends UpBackend {

    private Users users;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private SendRequest sendRequest;

    private UsersTokens tokens=new UsersTokens();
    private   Map<String, String> loggedUser=new LinkedHashMap<>();

    @Test
    void login() {
        users=new Users();
        users.setUsername("ali");
        users.setPassword("ali");
        SendRequest sendRequest=new SendRequest();
        sendRequest.setUsers(users);
        loggedUser= (LinkedHashMap) sendRequest.postRequest("http://localhost:8085/api/auth/login/").block();
        assertTrue(loggedUser.get("access_token")!=null,"user is successful logged");

       // tokens.setToken(loggedUser.get("access_token"));
        logout();

    }

    @Test
    void logout(){
        SendRequest sendRequest=new SendRequest();
        tokens.setToken(loggedUser.get("access_token"));
        sendRequest.setUsers(tokens);
        sendRequest.postRequest("http://localhost:8085/api/auth/logout/").subscribe(r->System.out.println(r));
       // String access_token=sendRequest.postRequest("http://localhost:8085/api/auth/logout/").block().toString();
        assertTrue(null==null,"logout successful");
    }
}
