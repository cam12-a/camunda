package ru.maralays.mfa.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_access_token")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token", length = 1024)
    private String token;
    @Column(name = "dateStart")
    private String dateStart;
    @Column(name = "expireTime")
    private String expireTime;
    @Column(name = "idFirebaseMessagingCloud")
    private String idFirebaseMessagingCloud;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private Users userToken;

    @ManyToOne
    @JoinColumn(name = "token_type_id")
    @JsonIgnore
    private TokenType tokenType;

    public String getToken() {
        return token;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserToken(Users userToken) {
        this.userToken = userToken;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }


    public String getIdFirebaseMessagingCloud() {
        return idFirebaseMessagingCloud;
    }

    public void setIdFirebaseMessagingCloud(String idFirebaseMessagingCloud) {
        this.idFirebaseMessagingCloud = idFirebaseMessagingCloud;
    }

    @Override
    public String toString() {
        return "UsersTokens{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", tokenType=" + tokenType +
                '}';
    }
}
