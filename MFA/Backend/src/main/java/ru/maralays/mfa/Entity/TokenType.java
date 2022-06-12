package ru.maralays.mfa.Entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "token_type")
public class TokenType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tokenName")
    private String tokenName;
    @OneToMany(mappedBy = "tokenType")
    private Set<UsersTokens> UsersTokenType;


    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
