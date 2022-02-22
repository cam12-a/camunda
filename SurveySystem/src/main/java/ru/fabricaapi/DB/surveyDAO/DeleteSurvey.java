package ru.fabricaapi.DB.surveyDAO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.fabricaapi.admin.question.model.SurveyTemplate;

@Component
public class DeleteSurvey {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SurveyTemplate question;
    public int deleteSurvey(int id){
        return jdbcTemplate.update("DELETE FROM survey WHERE fk_id_user = ?",id);
    }
    public int deleteSurvey(SurveyTemplate question){
        return jdbcTemplate.update("DELETE FROM survey WHERE question_name=? AND dateStart=? AND dateEnd=? AND description=?",
                question.getSurveyName(), question.getDateStart(), question.getDateEnd(),question.getDescription());
    }
}
