package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.fabricaapi.admin.question.Repository.SurveyRepository;
import ru.fabricaapi.admin.question.model.SurveyQuestion;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyDAO {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyTemplate surveyTemplate;
    @Autowired
    SurveyQuestionDAO surveyQuestionDAO;

   // @PersistenceContext
    // EntityManager entityManager;

    @Transactional
    public String createSurvey(SurveyTemplate surveyTemplate){
        try{
            if(findSurveyByAllSurveyParameters(surveyTemplate)==null)
               surveyRepository.save(surveyTemplate);
            else
                return "Данный опрос уже существует в БД";
        }catch (Exception ex){
            ex.printStackTrace();
            return "произошла ошибка при сохранении опроса";
        }
        return "Опрос успешно добавлен";
    }


/*
   public  <S extends SurveyTemplate> S save(S entity){
        if(!entityManager.contains(entity)){
            entityManager.persist(entity);
            return entity;
        }else
            return entityManager.merge(entity);
   }
   */


    public String deleteSurvey(int id){
        SurveyTemplate tempSurveyTemplate=surveyRepository.findById(id).orElse(null);
        System.out.println("log"+ tempSurveyTemplate.toString());
        if(tempSurveyTemplate==null)
            return "Данный опрос не существует";
        try {
            surveyRepository.deleteById(id);

        }catch (Exception ex){
            return "Произошла ошибку при удалении опроса";
        }
        return "опрос успешно удален";
    }


    public String deleteSurvey(SurveyTemplate survey){
        System.out.println("description "+survey.getDescription().isBlank());
        if(survey.getDescription()==null || survey.getDescription().isBlank())
            surveyTemplate=findSurveyByNameAndDates(survey);
        else
            surveyTemplate=findSurveyByAllSurveyParameters(survey);
        if(surveyTemplate!=null){
            surveyRepository.delete(surveyTemplate);
            return "Опрос "+survey.toString()+" успешно удален";
        }

        return "Невозможно удалить не существующий опрос";

    }
    public List<SurveyTemplate> findAllSurvey(){
        return surveyRepository.findAll() ;
    }

    public SurveyTemplate findSurveyById(int id){
        return surveyRepository.findById(id).get();
    }

    public String editSurvey(SurveyTemplate surveyTemplate){
        SurveyTemplate tempSurveyTemplate =findSurveyByNameAndDates(surveyTemplate);
        if(tempSurveyTemplate==null)
            return "Изменить данные о опросе который не существует, невозможно";
        try{
            surveyRepository.save(tempSurveyTemplate);

        }catch(Exception e){
            return "произошла ошибку при изменении опроса";
        }
        return "Опрос успешно изменено";
    }

    public SurveyTemplate findSurveyByNameAndDates(SurveyTemplate surveyTemplate){
        List<SurveyTemplate> tempSurveyTemplate=findAllSurvey();
        for(SurveyTemplate surveyTemplateList: tempSurveyTemplate){
            if(surveyTemplateList.getSurveyName().equals(surveyTemplate.getSurveyName()) &&
                surveyTemplateList.getDateEnd().getTime()==surveyTemplate.getDateEnd().getTime() &&
                    surveyTemplateList.getDateStart().getTime()==surveyTemplate.getDateStart().getTime()

            ){
                return surveyTemplateList;
            }
        }
        return null;
    }

    public SurveyTemplate findSurveyByAllSurveyParameters(SurveyTemplate surveyTemplate){
        try{
            return  surveyRepository.findBySurveyNameAndDateStartAndDateEnd(surveyTemplate.getSurveyName(),surveyTemplate.getDateStart(),surveyTemplate.getDateEnd());
        }catch(Exception e){
            return new SurveyTemplate();
        }

    }

    public List<SurveyTemplate> findSurveyByName(String surveyName){
        return surveyRepository.findBySurveyName(surveyName);
    }

}
