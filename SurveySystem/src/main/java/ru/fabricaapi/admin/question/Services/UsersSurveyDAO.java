package ru.fabricaapi.admin.question.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.UsersSurveyRepository;
import ru.fabricaapi.admin.question.model.UsersSurvey;

@Service
public class UsersSurveyDAO {

    @Autowired
    UsersSurveyRepository usersSurveyRepository;

    public void saveUserSurvey(UsersSurvey userSurvey){
        try {
                usersSurveyRepository.save(userSurvey);

        }catch (Exception e) {

        }

    }


}
