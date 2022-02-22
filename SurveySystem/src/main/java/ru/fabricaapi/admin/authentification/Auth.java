package ru.fabricaapi.admin.authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.fabricaapi.admin.question.Services.UsersDAO;
import ru.fabricaapi.admin.question.model.Users;

@RestController
@RequestMapping(value = "/api/auth")
public class Auth  {

    @Autowired
    UsersDAO usersDAO;

    @PostMapping(value = "/login/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String authentification(@RequestBody Users users){
        try{
            usersDAO.saveUsers(users);
        }catch(Exception e){

        }
        return "вы успешно зарегистрировались";
    }
    @PutMapping(value = "/editUser/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String editUser(@RequestBody Users users){
        return null;
    }


}
