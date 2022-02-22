package ru.fabricaapi.admin.question.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.SurveyQuestionRepository;
import ru.fabricaapi.admin.question.model.Answer;
import ru.fabricaapi.admin.question.model.SurveyQuestion;

import java.util.List;

@Service
public class SurveyQuestionDAO {
    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    public String insertSurveyQuestion(SurveyQuestion surveyQuestion){
        try{
            surveyQuestionRepository.save(surveyQuestion);
        }catch(Exception e){
            return "Произошла ошибку при добавлении ответа";
        }
        return null;
    }

    public List<SurveyQuestion> getAllSurveyQuestion(){
        return surveyQuestionRepository.findAll();
    }

}
