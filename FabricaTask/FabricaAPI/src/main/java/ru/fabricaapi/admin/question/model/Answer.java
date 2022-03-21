package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text_answer")
    @JsonProperty
    private String textAnswer;

    @OneToMany(mappedBy = "answer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference(value = "deserialize_answer")
    private Set<QuestionAnswer> answer ;

    @OneToMany(mappedBy = "userAnswer",fetch = FetchType.LAZY)
    @JsonBackReference(value = "user_answer")
    private Set<UsersSurvey> userAnswer;

}

