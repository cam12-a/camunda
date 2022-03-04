package ru.fabricaapi.admin.question.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "anonymous_survey")
@Data
@NoArgsConstructor
public class AnonymousSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
   // @JsonManagedReference
    private SurveyTemplate survey;
    @Column(name = "anonymous_id")
    private Long anonymousId;


}
