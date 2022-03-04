package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.fabricaapi.admin.question.model.Question;
import ru.fabricaapi.admin.question.model.SurveyQuestion;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import java.util.List;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Integer> {
   // <S extends SurveyQuestion> S delete(S entity);
    List<SurveyQuestion> findByQuestions(int surveyId);;
    SurveyQuestion findByQuestionsAndSurveyTemplates(Question question, SurveyTemplate survey);
    List<SurveyQuestion> findAllByOrderByIdAsc();
    List<SurveyQuestion> findBySurveyTemplates(SurveyTemplate survey);
}
