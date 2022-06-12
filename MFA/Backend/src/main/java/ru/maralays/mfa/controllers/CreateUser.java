package ru.maralays.mfa.controllers;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.Model.AuthModel;
import ru.maralays.mfa.Model.ResponseModel;
import ru.maralays.mfa.error.APIException;
import ru.maralays.mfa.security.TokenGenerator;
import ru.maralays.mfa.service.DAO.UsersDAO;
import ru.maralays.mfa.service.DAO.UsersTokensDAO;
import ru.maralays.mfa.service.interseptors.IntersectRequest;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This RestAPI allow users the create new account,
 * update their account,
 * get specific user
 * and return the user list
 * Login to the system
 * @author develepped by Alseny Camara
 * @version 1.0
 * @since 27/04/2022
 */

@RestController
@RequestMapping("/api/user/")
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@OpenAPIDefinition(info = @Info(title = "user api", description = "this resp api allow users to create, update and delete their account", termsOfService = "http://localhost:8080/api/user/", version = "1.0.1"))
public class CreateUser {

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    UsersTokensDAO usersTokensDAO;

    @Autowired
    APIException apiException;


    /**
     *
     * this method take the user template as argument
     * @param users
     * @return exception message when the user already exist, else return the new user with entered data
     */
    @CrossOrigin
    @PostMapping(value = "/new_account/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseModel> createNewUser(@RequestBody Users users){
        Users response= usersDAO.newUsers(users);

        if(response!=null)
            return new ResponseEntity<>(new ResponseModel("success",users), HttpStatus.ACCEPTED);
        return new ResponseEntity<>(new ResponseModel("Пользователь с таким логином уже существует",users),HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     *
     * @param users
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "edit_account/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseModel> updateUsersData(@RequestBody Users users){
        Users response= usersDAO.updateUsers(users.getOldUsername(),users);
        if(response!=null)
            return new ResponseEntity<>(new ResponseModel("data is successfull edited",response),HttpStatus.CREATED);
        Users tempUsers = usersDAO.findUsers(users.getUsername());
        tempUsers.setUsername(users.getOldUsername());
        return new ResponseEntity<>(new ResponseModel("data ",tempUsers), HttpStatus.NOT_FOUND);

    }
    @CrossOrigin
    @PostMapping(value = "edit_password/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseModel> updatePassword(@RequestBody Users users)
    {
        Users response=usersDAO.updatePassword(users.getOldPassword(),users.getUsername(),users.getPassword());
        return new ResponseEntity<>(new ResponseModel("password success edited",response),HttpStatus.ACCEPTED);
    }
    @CrossOrigin
    @GetMapping(value = "username/{username}")
    ResponseEntity<ResponseModel> findByUsername(@PathVariable String username){
        Users user=usersDAO.findUsers(username);
        if(user!=null)
            return new ResponseEntity<>(new ResponseModel("user found",user),HttpStatus.OK);
        return new ResponseEntity<>(new ResponseModel("user not found", null),HttpStatus.NOT_FOUND);
    }
    @CrossOrigin
    @GetMapping(value = "user/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseModel> findUsers(@RequestBody Users users) throws Throwable {
        Users user=usersDAO.userByUsernameAndPassword(users.getUsername(),users.getPassword());
        if(user!=null)
            return new ResponseEntity<>(new ResponseModel("users found",user),HttpStatus.OK);
        return new ResponseEntity<>(new ResponseModel("error user not found", null),HttpStatus.ACCEPTED);
    }




}



