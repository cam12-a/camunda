package ru.fabricaapi.admin.question.model;


import javax.persistence.*;

@Entity
@Table(name = "user_survey")
public class UsersSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users usersList;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyTemplate userSurveyTemplate;
}
