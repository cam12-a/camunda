package ru.fabricaapi.DB.surveyDAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

@Component
public class AddSurvey {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertSurvey(SurveyTemplate question){
        System.out.println("log "+question.toString());
        jdbcTemplate.update("INSERT INTO survey(fk_id_user,question_name,dateStart,dateEnd, description) VALUES (?, ?, ?, ?, ?)"
                ,question.getSurveyName(),question.getDateStart(),question.getDateEnd(),question.getDescription());
    }
}
