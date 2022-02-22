package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    @Column(name = "login")
    @JsonProperty
    private int login;
    @Column(name = "first_name")
    @JsonProperty
    private String firstName;
    @Column(name = "last_name")
    @JsonProperty
    private String lastName;
    @Column(name = "pwd")
    @JsonProperty
    private String pwd;

/*
    @ManyToMany
    @JoinTable(
            name = "users_survey",
            joinColumns=@JoinColumn(name = "user_id"),
            inverseJoinColumns=@JoinColumn(name = "survey_id")
    )
    Set<SurveyTemplate> userSurvey;
*/

    @OneToMany(mappedBy = "usersList")
    @JsonBackReference
    private Set<UsersSurvey> surveyPassedByUser;
}
