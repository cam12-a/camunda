package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@Data
@NoArgsConstructor
@Entity
@Table(name = "question")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@EqualsAndHashCode (exclude = "nameAttributeInThisClassWithOneToMany")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private int id;
    @JsonProperty
    @Column(name = "text_question",unique = true)
    private String textQuestion;
    @JsonProperty
    @Column(name = "question_type_id")
    private String questionType;


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference(value = "questionAnswer")
    @JsonProperty
    private List<QuestionAnswer> questionAnswer ;

    @Transient
    @JsonProperty
    private Map<String, Answer> parseAnswer;

    @Transient
    @JsonProperty
    private String oldTextQuestion;

    @OneToMany(mappedBy = "questions", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE,
            CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JsonBackReference(value = "deserialize_survey_question")
    private Set<SurveyQuestion> surveyQuestion;

    @OneToMany(mappedBy = "userQuestion",fetch = FetchType.LAZY)
    @JsonBackReference(value = "user_question")
    private Set<UsersSurvey> usersSurvey;


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", textQuestion='" + textQuestion + '\'' +
                ", questionType='" + questionType + '\'' +
                ", questionAnswer=" + questionAnswer +
                ", parseAnswer=" + parseAnswer +
                ", oldTextQuestion='" + oldTextQuestion + '\'' +

                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<QuestionAnswer> getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(List<QuestionAnswer> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Map<String, Answer> getParseAnswer() {
        return parseAnswer;
    }

    public void setParseAnswer(Map<String, Answer> parseAnswer) {
        this.parseAnswer = parseAnswer;
    }

    public String getOldTextQuestion() {
        return oldTextQuestion;
    }

    public void setOldTextQuestion(String oldTextQuestion) {
        this.oldTextQuestion = oldTextQuestion;
    }

    public Set<SurveyQuestion> getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(Set<SurveyQuestion> surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }
    /*@ManyToMany(mappedBy = "surveyTemplateQuestions",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SurveyTemplate> surveyQuestions;*/


}
