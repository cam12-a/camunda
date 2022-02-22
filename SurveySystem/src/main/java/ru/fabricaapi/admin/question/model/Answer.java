package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "answer")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text_answer")
    @JsonProperty
    private String textAnswer;


    @OneToMany(mappedBy = "answer",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<QuestionAnswer> answer ;

}

