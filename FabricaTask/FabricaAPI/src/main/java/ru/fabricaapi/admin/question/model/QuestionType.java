package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@NoArgsConstructor
//@Entity
//@Table(name = "question_type")
@Component
public class QuestionType {
    @Id
    @Column(columnDefinition = "serial")
    @JsonProperty
    private int id;
    @Column(name = "question_type_name",unique = true)
    @JsonProperty
    private String questionTypeName;
/*
    @OneToOne(mappedBy = "questionType",cascade = CascadeType.ALL)
    private Question question;
*/

}
