package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

//@Data
@NoArgsConstructor
@Entity
@Table(name = "survey_question")
@AllArgsConstructor
//@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
    @ManyToOne
    //@Cascade(value = {org.hibernate.annotations.CascadeType.DETACH})
    @JoinColumn(name = "survey_id",referencedColumnName = "id")
    //@JsonManagedReference
    private SurveyTemplate surveyTemplates;
    @ManyToOne
    //@JsonManagedReference
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    @JsonProperty
    private Question questions;

    @Override
    public String toString() {
        return "SurveyQuestion{" +
                "id=" + id +
                ", questions=" + questions +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SurveyTemplate getSurveyTemplates() {
        return surveyTemplates;
    }

    public void setSurveyTemplates(SurveyTemplate surveyTemplates) {
        this.surveyTemplates = surveyTemplates;
    }

    public Question getQuestions() {
        return questions;
    }

    public void setQuestions(Question questions) {
        this.questions = questions;
    }
}
