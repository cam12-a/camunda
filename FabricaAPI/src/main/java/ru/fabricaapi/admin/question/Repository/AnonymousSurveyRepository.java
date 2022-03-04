package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.AnonymousSurvey;

public interface AnonymousSurveyRepository extends JpaRepository<AnonymousSurvey,Integer> {
}
