package ru.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chat.entity.Users;


public interface UsersRep extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
    Users findByUsernameAndPassword(String username, String password);

}
