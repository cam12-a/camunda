package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.fabricaapi.admin.question.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}
