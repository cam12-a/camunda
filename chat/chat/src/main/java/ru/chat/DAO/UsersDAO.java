package ru.chat.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chat.entity.Users;
import ru.chat.repository.UsersRep;


@Service
public class UsersDAO {

    @Autowired
    UsersRep usersRep;

    public Users saveUsers(Users user){
        if(usersRep.findByUsername(user.getUsername())==null){
            return usersRep.save(user);
        }
        return new Users();
    }


    public String deleteUsers(Users user){
        if(findUsers(user.getUsername(),user.getPassword())!=null){
            usersRep.deleteById(findUsers(user.getUsername(),user.getPassword()).getId());
            return "user was  deleted";
        }
        else
            return "error while deleting user";
    }

    public Users updateUsers(Users user){
        Users tempUsers = findUsers(user.getUsername(),user.getPassword());
        if(tempUsers!=null){
            tempUsers.setFirstname(user.getFirstname());
            tempUsers.setLastname(user.getLastname());
            tempUsers.setPatronymic(user.getPatronymic());
            usersRep.save(tempUsers);
        }
        return  tempUsers;
    }

    public Users findUsers(String username, String password){
        Users tempUsers=usersRep.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        if(tempUsers==null)
            return null;
        else{
            if(bCryptPasswordEncoder.matches(password,tempUsers.getPassword())){
                return tempUsers;
            }
            else {
                return null;
            }
        }
    }

    public Users findByUsername(String username){
        return  usersRep.findByUsername(username);
    }



}
