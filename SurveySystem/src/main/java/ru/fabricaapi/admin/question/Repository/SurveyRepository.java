package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

public interface SurveyRepository extends JpaRepository<SurveyTemplate,Integer> {

}
