package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.Question;
import ru.fabricaapi.admin.question.model.QuestionAnswer;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    List<QuestionAnswer> findByQuestion(Question textQuestion);
}
