package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.UsersSurvey;

public interface UsersSurveyRepository extends JpaRepository<UsersSurvey, Integer> {
}
