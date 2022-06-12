package ru.maralays.mfa.service.DAO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import ru.maralays.mfa.Entity.Role;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersRoles;
import ru.maralays.mfa.error.APIException;
import ru.maralays.mfa.repository.RoleRepository;
import ru.maralays.mfa.repository.UsersRepository;
import ru.maralays.mfa.repository.UsersRolesRepository;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
@Slf4j
public class UsersDAO  {
//
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Autowired
    Role userRoles;

    @Autowired
    APIException apiException;



    @Transactional
    public Users newUsers(Users users){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
       users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        if(usersRepository.findByUsername(users.getUsername())==null) {
            log.info("new user {} is successful created",users.getUsername());
            log.error("id12 "+roleRepository.findByRoleName("USER_ROLE").toString());

            Set<UsersRoles> tempUsersRoles=new HashSet<>();

            users.getUsers().forEach(r->{
                UsersRoles usersRoles=new UsersRoles();
                Role tempRoles=new Role();
                usersRoles.setUsers(users);
                usersRoles.setRoles(roleRepository.findByRoleName(r.getRoles().getRoleName()));
                log.error("id25 {}",roleRepository.findByRoleName(r.getRoles().getRoleName()).toString());
                tempRoles.setRoleName(r.getRoles().getRoleName());
                log.info("in boucle {}", r.getRoles());
                tempUsersRoles.add(usersRoles);
            });
            users.setUsers(tempUsersRoles);
            log.error("tag "+users.getUsers().toString());

            return  usersRepository.save(users);
        }
        else{
            log.error("error to create user {}",users.getUsername());
            return new Users();
        }
    }

    public Users findUsers(String username){
        return usersRepository.findByUsername(username);
    }

    public Users updateUsers(String username, Users newUsers){
        Users tempUsers= usersRepository.findByUsername(username);
        if(tempUsers!=null) {
            if (tempUsers.getEmail().equals(newUsers.getEmail()) || tempUsers.getUsername().equals(newUsers.getUsername()))
                return new Users();
            tempUsers.setFirstname(newUsers.getFirstname());
            tempUsers.setLastname(newUsers.getLastname());
            tempUsers.setName(newUsers.getName());
            //tempUsers.setPassword(bCryptPasswordEncoder.encode(newUsers.getPassword()));
            return usersRepository.save(tempUsers);
        }
        return new Users();
    }

    public Users updatePassword(String oldPassword, String username, String newPassword){
        Users tempUsers= usersRepository.findByUsername(username);
        //tempUsers.setPassword(bCryptPasswordEncoder.encode(newPassword));
        if(tempUsers!=null) {
            tempUsers.setPassword(newPassword);
            usersRepository.save(tempUsers);
            return  tempUsers;
        }
        return new Users();

    }





    public Users userByUsernameAndPassword(String username, String password) throws Throwable {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        Users response = findUsers(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(response==null) {
            log.error("User not found {}",username);
            return null;
        }
        else {
            log.info("User  is found in the database"+ response);
            if (passwordEncoder.matches(password,response.getPassword())){
                log.info("correct password "+passwordEncoder.matches(response.getPassword(), password)+" "+
                        passwordEncoder.matches(response.getPassword(), bCryptPasswordEncoder.encode(password)));
                return response;
            }
            else {
                log.error("incorrect login or password");
                //throw apiException.CredentialException("Пользователя " + response.getUsername() + " не существует");
                Users users=new Users();
                users.setUsername("");
                users.setPassword("");
                users.setFirstname("");
                users.setLastname("");
                users.setName("");
                //return users;
                return null;
            }
        }

    }
}
