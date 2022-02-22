package ru.fabricaapi.admin.question.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import ru.fabricaapi.DB.surveyDAO.AddSurvey;
import ru.fabricaapi.DB.surveyDAO.DeleteSurvey;
import ru.fabricaapi.DB.surveyDAO.EditSurvey;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

public class Survey implements surveyManager {

    @Autowired
    protected SurveyTemplate surveyTemplate;
    @Autowired
    AddSurvey addSurvey;
    @Autowired
    DeleteSurvey deleteSurvey;
    @Autowired
    EditSurvey editSurvey;

    @Override
    public void saveItem(){
        addSurvey.insertSurvey(surveyTemplate);
    }

    @Override
    public int editItem() {
        return editSurvey.updateSurvey(surveyTemplate);
    }

    @Override
    public int deleteItem() {
        return deleteSurvey.deleteSurvey(surveyTemplate);
    }

    @Override
    public int deleteItem(int id) {
        return deleteSurvey.deleteSurvey(id);
    }

    @Override
    public void cloneObject() {

    }
}
