package ru.fabricaapi.DB.surveyDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

@Component
public class EditSurvey {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateSurvey(SurveyTemplate question){
        System.out.println("log "+question.toString());
        return jdbcTemplate.update("UPDATE survey SET question_name= ?, dateStart=?, dateEnd=?, description=? WHERE id=?",
                question.getSurveyName(),question.getDateStart(),question.getDateEnd(),question.getDescription(), question.getId());
    }

}
