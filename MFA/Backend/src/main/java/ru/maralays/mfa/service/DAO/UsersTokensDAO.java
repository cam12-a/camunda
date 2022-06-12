package ru.maralays.mfa.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.repository.UsersRolesRepository;
import ru.maralays.mfa.repository.UsersTokensRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class UsersTokensDAO {

    @Autowired
    private UsersTokensRepository usersTokensRepository;

    public UsersTokens saveTokens(UsersTokens usersTokens){
      return  usersTokensRepository.save(usersTokens);
    }

    public UsersTokens getLastToken(Users user, TokenType tokenType, String expireTime){

        return usersTokensRepository.findByUserTokenAndTokenTypeAndExpireTime(user, tokenType,expireTime);
    }

    public String destroyRefreshToken(String username){
        try{
           // usersTokensRepository.delete(getLastToken(username));
            return "user is disconnected";
        }catch (Exception e){
            return "error while disconnecting user";
        }
    }

    public List<UsersTokens> getAll(Users user){
        return usersTokensRepository.findByUserToken(user);
    }



    public UsersTokens findByUserTokenAndTokenTypeOrderById(Users users, TokenType tokenType){
        return usersTokensRepository.findByUserTokenAndTokenTypeOrderByIdDesc(users,tokenType).get(0);
    }


    public UsersTokens saveWithInitialization(Users users, TokenType tokenType, Long time_expire, String token, String idFirebaseMessagingCloud){
        UsersTokens usersTokens=new UsersTokens();
        usersTokens.setUserToken(users);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        usersTokens.setDateStart(formatter.format(new Date()));
        usersTokens.setExpireTime(formatter.format(new Date(time_expire)));

        usersTokens.setTokenType(tokenType);
        usersTokens.setIdFirebaseMessagingCloud(idFirebaseMessagingCloud);
        //BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        usersTokens.setToken(token);

        return saveTokens(usersTokens);
    }

    public UsersTokens saveWithInitialization(Users users, TokenType tokenType, Long time_expire, String token){
        UsersTokens usersTokens=new UsersTokens();
        usersTokens.setUserToken(users);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        usersTokens.setDateStart(formatter.format(new Date()));
        usersTokens.setExpireTime(formatter.format(new Date(time_expire)));

        usersTokens.setTokenType(tokenType);

        //BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        usersTokens.setToken(token);

        return saveTokens(usersTokens);
    }
}
