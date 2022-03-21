package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.*;

import java.util.List;

public interface UsersSurveyRepository extends JpaRepository<UsersSurvey, Integer> {

    UsersSurvey findByUsersListAndUserAnswerAndUserQuestionAndUserSurveyTemplate(Users users, Answer answer, Question question, SurveyTemplate survey);
    List<UsersSurvey> findByUserSurveyTemplate(SurveyTemplate survey);
    List<UsersSurvey> findByUsersList(Users user);
    List<UsersSurvey> findByUserGUI(Long id);

}
