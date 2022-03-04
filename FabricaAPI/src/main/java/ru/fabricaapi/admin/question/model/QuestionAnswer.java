package ru.fabricaapi.admin.question.model;




import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


//@Data
@NoArgsConstructor
@Component
@Entity
@Table(name = "question_answer")
@AllArgsConstructor
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonProperty("question")
   // @JsonManagedReference
    private Question question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    @JsonProperty("answer")
    //@JsonManagedReference
    private Answer answer;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
