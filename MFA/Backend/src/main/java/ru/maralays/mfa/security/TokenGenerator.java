package ru.maralays.mfa.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maralays.mfa.Entity.Users;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Component
public class TokenGenerator {

    private String secret="cd+Pr1js+w2qfT2BoCD+tPcYp9LbjpmhSMEJqUob1mcxZ7+Wmik4AYdjX+DlDjmE4yporzQ9tm7v3z/j+QbdYg==";
    //2629800000L
    private Algorithm algorithm=Algorithm.HMAC256(secret);
    private Long DEFAULT_EXPIRATION_TIME_ACCESS_TOKEN= new Date().getTime()+60000;
    private long DEFAULT_EXPIRATION_TIME_REFRESH_TOKEN=new Date().getTime()+120000 ;

    public Long getDEFAULT_EXPIRATION_TIME_ACCESS_TOKEN() {
        return DEFAULT_EXPIRATION_TIME_ACCESS_TOKEN;
    }

    public long getDEFAULT_EXPIRATION_TIME_REFRESH_TOKEN() {
        return DEFAULT_EXPIRATION_TIME_REFRESH_TOKEN;
    }

    public String generateToken(Users users, String issuer, Long expiration){

        return JWT.create()
                .withJWTId(users.getUsername())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("username",users.getUsername())
                .withClaim("password",users.getPassword())
                .withClaim("role", String.valueOf(users.getUsers()))
                .withExpiresAt(new Date(expiration))
                .sign(algorithm);

    }


    public Boolean verifyToken(String token, String issuer, Long expiration){

        try{
           JWTVerifier verifier= JWT.require(this.algorithm)
                    .acceptExpiresAt(new Date(expiration).getTime())
                    .build();
           verifier.verify(token);
           log.info("token is verified");
            return true;

        }catch(JWTVerificationException e){
            log.error("error to verify token ", e);

        }
        return false;
    }

    public String getClaimFromToken(String token,String claimKey){
        DecodedJWT decodedJWT=JWT.decode(token);
        return decodedJWT.getClaims().get(claimKey).toString();
    }


}
