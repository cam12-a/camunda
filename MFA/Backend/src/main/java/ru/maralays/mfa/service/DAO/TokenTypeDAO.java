package ru.maralays.mfa.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.repository.TokenTypeRepository;

@Component
public class TokenTypeDAO {

    @Autowired
    TokenTypeRepository tokenTypeRepository;

    public TokenType getTokenTypeByName(String tokenName){
        return tokenTypeRepository.findByTokenName(tokenName);
    }
}
