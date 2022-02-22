package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "survey_question")
@AllArgsConstructor
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonManagedReference

    private SurveyTemplate surveyTemplates;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "question_id")
    private Question questions;


}
