package ru.maralays.mfa.service.Tokens;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.maralays.mfa.Entity.TokenType;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.Entity.UsersTokens;
import ru.maralays.mfa.security.TokenGenerator;
import ru.maralays.mfa.service.DAO.UsersTokensDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TokenCheck {

    @Autowired
    UsersTokensDAO usersTokensDAO;


    public Boolean checkToken(Users users, TokenGenerator tokenGenerator, String token, TokenType tokenType, String issuer) throws ParseException {


        UsersTokens token_access_from_db=usersTokensDAO.findByUserTokenAndTokenTypeOrderById(users,tokenType);

        log.error("token from bd "+token_access_from_db.toString());
        String expire=token_access_from_db.getExpireTime();
        Long current_date=new Date().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date expire_time=formatter.parse(expire);
        log.error("expire "+expire+" "+expire_time.getTime() +" date in "+new Date(current_date)+" "+current_date);

        if(tokenGenerator.verifyToken(token,issuer,expire_time.getTime())){

            log.info("url "+ ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());

           /* if(issuer.equals("refresh_token") && expire_time.getTime()<current_date) {
                log.error("r token " + token_access_from_db.toString());
                return false;
            }*/

            if(expire_time.getTime()<current_date) {
                log.error("current_date "+current_date);
                return true;
            }
            else{


                return false;
            }

        }

        return false;
    }

}
