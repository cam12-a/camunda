package ru.fabricaapi.admin.question.Services.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fabricaapi.admin.question.Repository.AnonymousSurveyRepository;
import ru.fabricaapi.admin.question.Repository.UsersSurveyRepository;
import ru.fabricaapi.admin.question.model.*;

import java.util.List;

@Service
public class UsersSurveyDAO {

    @Autowired
    UsersSurveyRepository usersSurveyRepository;
    @Autowired
    AnonymousSurveyRepository anonymousSurveyRepository;

    public void saveUserSurvey(UsersSurvey userSurvey){
        try {
                usersSurveyRepository.save(userSurvey);

        }catch (Exception e) {

        }

    }

    public void saveUserSurvey(AnonymousSurvey anonymousSurvey){
        anonymousSurveyRepository.save(anonymousSurvey);
    }

    public List<UsersSurvey> findAllUsersSurvey(){
        return usersSurveyRepository.findAll();
    }

    public List<UsersSurvey> findUsersSurveyBySurveyName(SurveyTemplate survey){
        return  usersSurveyRepository.findByUserSurveyTemplate(survey);
    }

    public UsersSurvey findSurveyByAllSurveyParameters(Users users, Answer answer, Question question, SurveyTemplate survey){
        return usersSurveyRepository.findByUsersListAndUserAnswerAndUserQuestionAndUserSurveyTemplate(users,answer,question,survey);
    }

    public List<UsersSurvey> findByUsername(Users user){
        return  usersSurveyRepository.findByUsersList(user);
    }

    public List<UsersSurvey>findByGUI(Long id){
        return  usersSurveyRepository.findByUserGUI(id);
    }


}
