package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;

import java.util.List;

public interface UsersTokensRepository extends JpaRepository<UsersTokens,Long> {
    List<UsersTokens> findByUserToken(Users user);
    UsersTokens findByUserTokenAndTokenTypeAndExpireTime(Users user, TokenType tokenType, String expireTime);
    List<UsersTokens> findByUserTokenAndTokenTypeOrderByIdDesc(Users user,TokenType tokenType);
}
