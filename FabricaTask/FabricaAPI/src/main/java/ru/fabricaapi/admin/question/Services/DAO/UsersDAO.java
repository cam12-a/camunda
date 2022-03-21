package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.UsersRepository;
import ru.fabricaapi.admin.question.model.Role;
import ru.fabricaapi.admin.question.model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Service

public class UsersDAO implements UserDetailsService {



    @PersistenceContext
    private EntityManager em;
    @Autowired
    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();


    public boolean saveUsers(Users users){

        Users userFromDB = usersRepository.findByUsername(users.getUsername());

        if (userFromDB != null) {
            return false;
        }

        users.setPwd(bCryptPasswordEncoder.encode((users.getPwd())));
        usersRepository.save(users);
        return true;
    }



    public Users editUsers(Users users){
        usersRepository.save(users);
        return loadUserByUsername(users.getUsername());
    }

    public void resetPWD(String login, String newPWD){
        Users tempUsers=loadUserByUsername(login);
        tempUsers.setPwd(bCryptPasswordEncoder.encode(newPWD));
    }

    public String updatePWD(String login, String currentPWD, String newPWD){

        Users tempUsers=loadUserByUsername(login);
        if(tempUsers.getPwd().equals(currentPWD)){

            return "Пароль успешно изменен";
        }
        return "Настоящий пароль не вереный";
    }



    public String deleteUsers(String login){
        Users users=loadUserByUsername(login);
        if(users!=null) {
            usersRepository.delete(users);
            return "пользователь успешно удален из системы";
        }
        return "Такого пользователя не существует";

    }

    public List<Users> getUsersList(){
        return usersRepository.findAll();
    }

    @Override
    public Users loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public Users findUserById(int userId) {
        Optional<Users> userFromDb = usersRepository.findById(userId);
        return userFromDb.orElse(new Users());
    }

    public Users extendUserRole(String username, String roleName){
        Users user=loadUserByUsername(username);

        user.setRoles(Collections.singleton(new Role(2,roleName)));

        saveUsers(user);

        return loadUserByUsername(user.getUsername());
    }

}
