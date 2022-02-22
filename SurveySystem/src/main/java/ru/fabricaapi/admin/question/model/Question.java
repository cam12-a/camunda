package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "question")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @JsonProperty
    private int id;
    @JsonProperty
    @Column(name = "text_question",unique = true)
    private String textQuestion;
    @JsonProperty
    @Column(name = "question_type_id")
    private String questionType;


    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<QuestionAnswer> questionAnswer ;

    @OneToMany(mappedBy = "questions",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SurveyQuestion> surveyQuestion;
    /*@ManyToMany(mappedBy = "surveyTemplateQuestions",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SurveyTemplate> surveyQuestions;*/


}
