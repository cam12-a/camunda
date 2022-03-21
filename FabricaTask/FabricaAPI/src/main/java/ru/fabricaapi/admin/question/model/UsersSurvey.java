package ru.fabricaapi.admin.question.model;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_survey")
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @Column(name = "userGUI")
    @JsonProperty("userGUI")
    private Long userGUI;

    @JsonProperty
    @Transient
    private boolean anonymous;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty("usersList")
    private Users usersList;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    //@JsonManagedReference
    @JsonProperty("userSurveyTemplate")
    private SurveyTemplate userSurveyTemplate;
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonProperty("userQuestion")
    private Question userQuestion;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    @JsonProperty("userAnswer")
    private Answer userAnswer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserGUI() {
        return userGUI;
    }

    public void setUserGUI(Long userGUI) {
        this.userGUI = userGUI;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Users getUsersList() {
        return usersList;
    }

    public void setUsersList(Users usersList) {
        this.usersList = usersList;
    }

    public SurveyTemplate getUserSurveyTemplate() {
        return userSurveyTemplate;
    }

    public void setUserSurveyTemplate(SurveyTemplate userSurveyTemplate) {
        this.userSurveyTemplate = userSurveyTemplate;
    }

    public Question getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(Question userQuestion) {
        this.userQuestion = userQuestion;
    }

    public Answer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Answer userAnswer) {
        this.userAnswer = userAnswer;
    }
}
