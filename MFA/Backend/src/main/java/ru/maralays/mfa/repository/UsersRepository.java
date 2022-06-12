package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maralays.mfa.Entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
        Users findByUsername(String username);
        Users findByEmail(String email);
        Users findByUsernameAndPassword(String username, String password);
}
