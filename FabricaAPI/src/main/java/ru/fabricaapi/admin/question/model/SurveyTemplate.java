package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "survey")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public  class SurveyTemplate {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    @Override
    public String toString() {
        return "SurveyTemplate{" +
                "id=" + id +
                ", surveyName='" + surveyName + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", description='" + description + '\'' +
                ", surveyTemplateQuestions"+ surveyTemplateQuestions+'\''+
                '}';
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SurveyQuestion> getSurveyTemplateQuestions() {
        return surveyTemplateQuestions;
    }

    public void setSurveyTemplateQuestions(List<SurveyQuestion> surveyTemplateQuestions) {
        this.surveyTemplateQuestions = surveyTemplateQuestions;
    }

    public Set<UsersSurvey> getUsersSurvey() {
        return usersSurvey;
    }

    public void setUsersSurvey(Set<UsersSurvey> usersSurvey) {
        this.usersSurvey = usersSurvey;
    }

    public Set<AnonymousSurvey> getAnonymousSurveys() {
        return anonymousSurveys;
    }

    public void setAnonymousSurveys(Set<AnonymousSurvey> anonymousSurveys) {
        this.anonymousSurveys = anonymousSurveys;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private int id;
    @JsonProperty
    @Column(name="survey_name")
    private String surveyName;
    @JsonProperty
    @Column(name = "datestart")
    private Date dateStart;
    @JsonProperty
    @Column(name = "dateend")
    private Date dateEnd;
    @JsonProperty
    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "surveyTemplates", orphanRemoval = true)
    @JsonBackReference(value="survey_question")
    @Fetch(value= FetchMode.SELECT)
    @JsonProperty
    private List<SurveyQuestion> surveyTemplateQuestions;



    @OneToMany(mappedBy = "userSurveyTemplate",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "survey_users")
    @Fetch(value= FetchMode.SELECT)
    private Set<UsersSurvey> usersSurvey;

    @OneToMany(mappedBy = "anonymousId",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "anonymous_survey")
    @Fetch(value=FetchMode.SELECT)
    private Set<AnonymousSurvey> anonymousSurveys;
}
