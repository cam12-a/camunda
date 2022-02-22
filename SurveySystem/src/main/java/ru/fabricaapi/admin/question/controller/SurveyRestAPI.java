package ru.fabricaapi.admin.question.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.fabricaapi.admin.question.Entity.Survey;
//import ru.fabricaapi.admin.question.Services.SurveyDAO;
import ru.fabricaapi.admin.question.Repository.SurveyQuestionRepository;
import ru.fabricaapi.admin.question.Services.Parse.ParseSurvey;
import ru.fabricaapi.admin.question.Services.QuestionDAO;
import ru.fabricaapi.admin.question.Services.SurveyDAO;
import ru.fabricaapi.admin.question.Services.SurveyQuestionDAO;
import ru.fabricaapi.admin.question.model.Question;
import ru.fabricaapi.admin.question.model.SurveyQuestion;
import ru.fabricaapi.admin.question.model.SurveyTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/survey/")
public class SurveyRestAPI {

    @Autowired
    SurveyDAO surveyDAO;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SurveyQuestionDAO surveyQuestionDAO;

    @PostMapping(value = "/addSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String add(@RequestBody ParseSurvey survey) {

        SurveyTemplate surveyTemplate=new SurveyTemplate();
        surveyTemplate.setSurveyName(survey.getSurveyName());
        surveyTemplate.setDateStart(survey.getDateStart());
        surveyTemplate.setDescription(survey.getDescription());
        surveyTemplate.setDateEnd(survey.getDateEnd());

        surveyDAO.createSurvey(surveyTemplate);


        System.out.println("length "+survey.getQuestions()+" surveyAl "+survey.toString());
        Set<SurveyQuestion> surveyQuestionSet=new HashSet<>();
        for(String textQuestion:survey.getQuestions()){

            SurveyQuestion surveyQuestions=new SurveyQuestion();

            Question question=questionDAO.findQuestion(textQuestion);
            surveyQuestions.setQuestions(question);

            surveyQuestions.setSurveyTemplates(surveyTemplate);

            //surveyQuestionSet.add(surveyQuestions);

            surveyQuestionDAO.insertSurveyQuestion(surveyQuestions);




        }
        return null;
    }

    @PostMapping(value = "/test/",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void check(@RequestBody SurveyTemplate question){
        System.out.println(question.toString());
    }

    @PutMapping(value = "/editSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String edit(@RequestBody SurveyTemplate survey) {
       return surveyDAO.editSurvey(survey);
    }


    @DeleteMapping("/deleteSurvey/{id}")
    public String deleteSurvey(@PathVariable int id) {
        return surveyDAO.deleteSurvey(id);
    }

    @DeleteMapping(value = "/deleteSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteSurvey(@RequestBody SurveyTemplate survey) {

        return surveyDAO.deleteSurvey(survey);
    }
    @GetMapping(value = "/{id}")
    public SurveyTemplate getSurveyById(@PathVariable int id){
        return surveyDAO.findSurveyById(id);
    }
    @GetMapping(value = "/allSurvey/")
    public List<SurveyTemplate> getAll(){
        return surveyDAO.findAllSurvey();
    }
    @GetMapping(value = "/survey/question/")
    public List<SurveyQuestion> allSurveyQuestion(){
        return surveyQuestionDAO.getAllSurveyQuestion();
    }
    @GetMapping(value = "/survey/{surveyName}")
    public List<SurveyTemplate> getSurveyByName(@PathVariable String surveyName){
        return surveyDAO.findSurveyByName(surveyName);
    }

}
