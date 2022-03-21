package ru.fabricaapi.admin.authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.fabricaapi.admin.question.Services.DAO.UsersDAO;

import ru.fabricaapi.admin.question.model.Role;
import ru.fabricaapi.admin.question.model.Users;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth/")
/**
 *
 *
 */
public class Auth  {

    @Autowired
    UsersDAO usersDAO;

    /**
     *
     * @param users
     * @return
     */
    @PostMapping(value = "/singIn/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String singIn(@RequestBody Users users){
        users.setRoles(Collections.singleton(new Role(1, "SIMPLE_USER")));
        if(usersDAO.saveUsers(users))
            return "Пользователь успешно зарегистрирован";
        else
            return "Пользователь с таким логином уже существует";

    }

    @PostMapping(value = "/login/",consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users login(@RequestBody Users authData){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        Users users=usersDAO.loadUserByUsername(authData.getUsername());
        if(bCryptPasswordEncoder.matches(authData.getPwd(),users.getPassword()))
            return usersDAO.loadUserByUsername(authData.getUsername());
        else
            return new Users();
    }

    @PutMapping(value = "/editUser/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users editUser(@RequestBody Users users){
        Users user=new Users();
        user=usersDAO.loadUserByUsername(users.getUsername());
        user.setLastName(users.getLastName());
        user.setFirstName(users.getFirstName());
        users.setUsername(users.getUsername());

        return usersDAO.editUsers(user);

    }

    @PostMapping(value = "/extendsRole/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users extendsRole(@RequestBody Users authData){
      // return usersDAO.extendUserRole(authData.getUsername(), authData.getRoles());
        return new Users();
    }

}
