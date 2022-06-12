package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maralays.mfa.Entity.TokenType;

public interface TokenTypeRepository extends JpaRepository<TokenType, Long> {
    TokenType findByTokenName(String tokenName);
}
