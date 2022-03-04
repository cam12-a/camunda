package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import java.util.Date;
import java.util.List;


public interface SurveyRepository extends JpaRepository<SurveyTemplate,Integer> {
    <S extends SurveyTemplate> S save(S entity);
    SurveyTemplate findBySurveyNameAndDateStartAndDateEnd(String surveyName, Date dateStart, Date dateEnd);
    List<SurveyTemplate> findBySurveyName(String surveyName);
}
