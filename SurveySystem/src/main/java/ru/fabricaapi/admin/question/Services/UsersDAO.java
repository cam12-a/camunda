package ru.fabricaapi.admin.question.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.UsersRepository;
import ru.fabricaapi.admin.question.model.Users;

@Service
public class UsersDAO {

    @Autowired
    UsersRepository usersRepository;

    public void saveUsers(Users users){
        usersRepository.save(users);
    }
    public void editUsers(Users users){

    }

}
