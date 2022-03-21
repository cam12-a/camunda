package ru.fabricaapi.admin.question.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "password_token_reset")
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Users usersPasswordToken;
    @Column(name = "token")
    private String token;
    @Column(name = "expirationTime")
    private Time expiration;


}
