package ru.fabricaapi.admin.question.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.fabricaapi.admin.question.Repository.SurveyRepository;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyDAO {
    @Autowired
    SurveyRepository surveyRepository;

    public String createSurvey(SurveyTemplate surveyTemplate){
        try{
            surveyRepository.save(surveyTemplate);
        }catch (Exception ex){
            return "произошла ошибка при сохранении опроса";
        }
        return "Опрос успешно добавлен";
    }
    public String deleteSurvey(SurveyTemplate surveyTemplate){
        SurveyTemplate tempSurveyTemplate=surveyRepository.findById(surveyTemplate.getId()).orElse(null);
        if(tempSurveyTemplate==null)
            return "Данный опрос не существует";
        try {
            surveyRepository.delete(surveyTemplate);
        }catch (Exception ex){
            return "Произошла ошибку при удалении опроса";
        }
        return "опрос успешно удален";
    }
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
    public List<SurveyTemplate> findAllSurvey(){
        return surveyRepository.findAll();
    }

    public SurveyTemplate findSurveyById(int id){
        return surveyRepository.findById(id).get();
    }

    public String editSurvey(SurveyTemplate surveyTemplate){
        SurveyTemplate tempSurveyTemplate =findSurveyById(surveyTemplate.getId());
        if(tempSurveyTemplate==null)
            return "Изменить данные о опросе который не существует, невозможно";
        tempSurveyTemplate.setSurveyName(surveyTemplate.getSurveyName());
        tempSurveyTemplate.setDateEnd(surveyTemplate.getDateEnd());
        tempSurveyTemplate.setDateStart(surveyTemplate.getDateStart());
        tempSurveyTemplate.setDescription(surveyTemplate.getDescription());
        try{
            surveyRepository.save(tempSurveyTemplate);

        }catch(Exception e){
            return "произошла ошибку при изменении опроса";
        }
        return "Опрос успешно изменено";
    }

    public int findSurveyById(SurveyTemplate surveyTemplate){
        List<SurveyTemplate> tempSurveyTemplate=findAllSurvey();
        for(SurveyTemplate surveyTemplateList: tempSurveyTemplate){
            if(surveyTemplateList.getSurveyName().equals(surveyTemplate.getSurveyName()) &&
                surveyTemplateList.getDateEnd().equals(surveyTemplate.getDateEnd()) &&
                    surveyTemplateList.getDateStart().equals(surveyTemplate.getDateStart()) &&
                    surveyTemplateList.getDescription().equals(surveyTemplate.getDescription())

            ){
                return surveyTemplateList.getId();
            }
        }
        return -1;
    }

    public List<SurveyTemplate> findSurveyByName(String surveyName){
        List<SurveyTemplate> surveyTemplate=surveyRepository.findAll();
        List<SurveyTemplate> result=new ArrayList<>();
        for(SurveyTemplate survey:surveyTemplate){
            if(survey.getSurveyName().equals(surveyName))
                result.add(survey);
        }
        return result;
    }

}
