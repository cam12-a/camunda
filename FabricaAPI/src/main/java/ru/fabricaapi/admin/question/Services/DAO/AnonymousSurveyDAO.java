package ru.fabricaapi.admin.question.Services.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.AnonymousSurveyRepository;
import ru.fabricaapi.admin.question.model.AnonymousSurvey;

@Service
public class AnonymousSurveyDAO {

    @Autowired
    AnonymousSurveyRepository anonymousSurveyRepository;

    public void saveAnonymousSurvey(AnonymousSurvey anonymousSurvey){
        anonymousSurveyRepository.save(anonymousSurvey);
    }



}
