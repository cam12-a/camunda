package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fabricaapi.admin.question.Repository.SurveyQuestionRepository;
import ru.fabricaapi.admin.question.model.Answer;
import ru.fabricaapi.admin.question.model.Question;
import ru.fabricaapi.admin.question.model.SurveyQuestion;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SurveyQuestionDAO {
    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @PersistenceContext
    EntityManager entity;

    public String insertSurveyQuestion(SurveyQuestion surveyQuestion){
        try{
            surveyQuestionRepository.save(surveyQuestion);
        }catch(Exception e){
            return "Произошла ошибку при добавлении ответа";
        }
        return null;
    }

    @Transactional
    public String deleteSurveyQuestion(SurveyQuestion surveyQuestion){

        surveyQuestionRepository.deleteById(surveyQuestion.getId());

        return null;
    }
/*
    public <S extends SurveyQuestion> S delete(S entity){
        if(!entityManager.contains(entity))
            entityManager.detach(entity);
        return entity;
    }

*/

    public List<SurveyQuestion> getAllSurveyQuestion(){
       // System.out.println("ss "+surveyQuestionRepository.findAll());
        try {
            return surveyQuestionRepository.findAllByOrderByIdAsc();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<SurveyQuestion> findSurveyById(int surveyId){
        return surveyQuestionRepository.findByQuestions(surveyId);
    }

    public SurveyQuestion findSurveyQuestionBySurveyAndQuestionIds(Question question,SurveyTemplate survey){
        return surveyQuestionRepository.findByQuestionsAndSurveyTemplates(question,survey);
    }

    public List<SurveyQuestion> findSurvey(SurveyTemplate survey){
        return surveyQuestionRepository.findBySurveyTemplates(survey);
    }



}
