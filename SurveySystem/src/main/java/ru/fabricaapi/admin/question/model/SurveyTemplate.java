package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "survey")
public  class SurveyTemplate {

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

  /*  @ManyToMany

    @JoinTable(
            name = "survey_question",
            joinColumns=@JoinColumn(name = "survey_id",referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name = "question_id",referencedColumnName = "id")
    )
    @JsonManagedReference*/

    @OneToMany(mappedBy = "surveyTemplates",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SurveyQuestion> surveyTemplateQuestions;

    @OneToMany(mappedBy = "userSurveyTemplate")
    @JsonBackReference
    private Set<UsersSurvey> usersSurvey;
}
