package ru.chat.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.chat.DAO.UsersDAO;
import ru.chat.entity.Users;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/api/user/")
@OpenAPIDefinition(info=@Info(title = "manage user",description = "this is an api that create, delete or update user's data ",termsOfService = "http://172.17.137.45:8088/api/auth/",version = "1.0"))
@Slf4j
public class UsersAPI {

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Autowired
    UsersDAO usersDAO;

    @PostMapping(value="/create/",consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> createUsers(@RequestBody Users user){
        log.error(user.toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       // usersDAO.saveUsers(user)
        return new ResponseEntity<>(usersDAO.saveUsers(user), HttpStatus.OK);
    }

    @PutMapping(value = "edit/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> editUsers(@RequestBody Users user, HttpServletRequest request, HttpServletResponse response){

        return new ResponseEntity<>(usersDAO.updateUsers(user), HttpStatus.OK);
    }

    @DeleteMapping(value="delete/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUsers(@RequestBody Users user, HttpServletRequest request, HttpServletResponse response){
        log.error("delete "+user);
        return new ResponseEntity<>(usersDAO.deleteUsers(user), HttpStatus.OK);
    }




}
