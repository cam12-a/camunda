package ru.fabricaapi.admin.question.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import ru.fabricaapi.admin.authentification.Auth;
import ru.fabricaapi.admin.question.Services.DAO.*;

import ru.fabricaapi.admin.question.Services.Parse.ParseEditQuestion;
import ru.fabricaapi.admin.question.model.*;

import java.util.*;

@RestController
@RequestMapping("/api/survey/")
@OpenAPIDefinition(info =@Info(title = "Опрос", description = "Данный API служить для прохождения опроса",termsOfService = "localhost:8089/api/survey",version = "1.0.1"))
public class SurveyRestAPI {

    @Autowired
    SurveyDAO surveyDAO;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SurveyQuestionDAO surveyQuestionDAO;

    @Autowired
    UsersSurveyDAO usersSurveyDAO;
    @Autowired
    QuestionAnswerDAO questionAnswerDAO;

    @Autowired
    Auth authData;
    @Autowired
    UsersDAO usersDAO;

    @Autowired
    AnswerDAO answerDAO;

    @PostMapping(value = "/addSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String add(@RequestBody SurveyTemplate survey) {

        String as;
        SurveyTemplate currentSurvey=surveyDAO.findSurveyByAllSurveyParameters(survey);
        if(currentSurvey!=null) {
           for(SurveyQuestion surveyQuestion: survey.getSurveyTemplateQuestions()){
                Question question=questionDAO.findQuestionByTextQuestion(surveyQuestion.getQuestions().getTextQuestion());
                System.out.println("question "+question.getTextQuestion()+" "+question.getQuestionType());
                surveyQuestion.getSurveyTemplates().setId(currentSurvey.getId());
                surveyQuestion.getQuestions().setId(question.getId());
                surveyQuestion.getQuestions().setTextQuestion(question.getTextQuestion());
                surveyQuestion.getQuestions().setQuestionType(question.getQuestionType());
                System.out.println("add if exist"+ surveyQuestionDAO.findSurveyQuestionBySurveyAndQuestionIds(question,currentSurvey));

                if(surveyQuestionDAO.findSurveyQuestionBySurveyAndQuestionIds(question,currentSurvey)==null){
                  surveyQuestionDAO.insertSurveyQuestion(surveyQuestion);
                }

           }
            return "Не существующие вопросы были добавлены в опрос";
        }
        surveyDAO.createSurvey(survey);
        saveSurveyQuestionIfNotExists(survey);

        return "Опрос успешно создан";
    }




    public void saveSurveyQuestionIfNotExists(SurveyTemplate survey){

        for(SurveyQuestion surveyQuestion: survey.getSurveyTemplateQuestions()) {

           Question question = questionDAO.findQuestionByTextQuestion(surveyQuestion.getQuestions().getTextQuestion());
            System.out.println("ok "+question.getId());

            initializeSurveyQuestion(surveyQuestion,question,survey);

            SurveyTemplate surveyTemplate=surveyDAO.findSurveyByAllSurveyParameters(surveyQuestion.getSurveyTemplates());
            surveyQuestion.getSurveyTemplates().setId(surveyTemplate.getId());

            surveyQuestionDAO.insertSurveyQuestion(surveyQuestion);

        }
    }

    public void initializeSurveyQuestion(SurveyQuestion surveyQuestion, Question question, SurveyTemplate survey){
        surveyQuestion.getQuestions().setId(question.getId());
        surveyQuestion.getQuestions().setQuestionType(question.getQuestionType());
        surveyQuestion.getSurveyTemplates().setSurveyName(survey.getSurveyName());
        surveyQuestion.getSurveyTemplates().setDescription(survey.getDescription());
        surveyQuestion.getSurveyTemplates().setDateEnd(survey.getDateEnd());
        surveyQuestion.getSurveyTemplates().setDateStart(survey.getDateStart());

    }

    @PostMapping(value = "/test/",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void check(@RequestBody SurveyTemplate question){
        System.out.println(question.toString());
    }

    @DeleteMapping(value = "/delete/question/fromSurvey/",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SurveyQuestion> deleteQuestionFromSurvey(@RequestBody SurveyTemplate survey){


        List<SurveyQuestion> tempSurveyQuestion=surveyQuestionDAO.getAllSurveyQuestion();
        System.out.println("survey "+survey);

        int deleteSurveyFromSurveyTable=0;



        for(SurveyQuestion surveyQuestion: survey.getSurveyTemplateQuestions()){

            Question question=questionDAO.findQuestionByTextQuestion(surveyQuestion.getQuestions().getTextQuestion());
            SurveyTemplate surveyTemplate=surveyDAO.findSurveyByAllSurveyParameters(survey);

            initializeSurveyQuestion(surveyQuestion,question,survey);

            if(question!=null && surveyTemplate!=null){

                surveyQuestion.setId(question.getId());
                surveyQuestion.getSurveyTemplates().setId(surveyTemplate.getId());
                System.out.println("delete 1"+surveyQuestion);
                for(SurveyQuestion temp: tempSurveyQuestion){
                    if(temp.getQuestions().getId()==question.getId() && temp.getSurveyTemplates().getId()==surveyTemplate.getId()){
                        System.out.println("dele "+surveyQuestion);
                        deleteSurveyFromSurveyTable++;
                        surveyQuestionDAO.deleteSurveyQuestion(temp);
                    }
                }

                //surveyQuestionDAO.deleteSurveyQuestion(surveyQuestion);

            }
            else{
                continue;
            }
        }
        if(survey.getSurveyTemplateQuestions().size()==deleteSurveyFromSurveyTable)
            surveyDAO.deleteSurvey(survey);

        return surveyQuestionDAO.getAllSurveyQuestion();


    }

    @PutMapping(value = "/editSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String edit(@RequestBody ParseEditQuestion survey) {

        SurveyTemplate tempSurveyTemplate=new SurveyTemplate();

        tempSurveyTemplate.setSurveyName(survey.getCurrentSurvey().getSurveyName());
        tempSurveyTemplate.setDescription(survey.getCurrentSurvey().getDescription());
        tempSurveyTemplate.setDateEnd(survey.getCurrentSurvey().getDateEnd());
        tempSurveyTemplate.setDateStart(survey.getCurrentSurvey().getDateStart());

        SurveyTemplate currentSurvey=surveyDAO.findSurveyByNameAndDates(tempSurveyTemplate);


        if(currentSurvey!=null) {

            currentSurvey.setSurveyName(survey.getNewSurvey().getSurveyName());
            currentSurvey.setDateEnd(survey.getNewSurvey().getDateEnd());
            currentSurvey.setDescription(survey.getNewSurvey().getDescription());
           return surveyDAO.editSurvey(currentSurvey);
        }
        else
            return "Возникла ошибка при изменении опроса";
    }


    @DeleteMapping("/deleteSurvey/{id}")
    public String deleteSurvey(@PathVariable int id) {
        return surveyDAO.deleteSurvey(id);
    }



    @DeleteMapping(value = "/deleteSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteSurvey(@RequestBody SurveyTemplate survey) {
        System.out.println("survey "+survey.toString());
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
        //return surveyDAO.findAllSurvey();
    }
    @GetMapping(value = "/survey/{surveyName}")
    public List<SurveyTemplate> getSurveyByName(@PathVariable String surveyName){
        return surveyDAO.findSurveyByName(surveyName);
    }

    @GetMapping(value = "/surveyQuestion/", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SurveyQuestion> surveyQuestionBySurveyName(@RequestBody SurveyTemplate survey){

        survey=surveyDAO.findSurveyByAllSurveyParameters(survey);
        System.out.println(survey);
        String answerNumber="answer";
        int i=1;
        List<SurveyQuestion> surveyQuestion=surveyQuestionDAO.findSurvey(survey);

        for(SurveyQuestion surveyQuestions: surveyQuestion){
            Map<String, Answer> questionAnswer=new HashMap<>();
            List<QuestionAnswer> answer=questionAnswerDAO.findAnswer(surveyQuestions.getQuestions());
            for(QuestionAnswer answers:answer){
                questionAnswer.put(answerNumber+(i++),answers.getAnswer());
                surveyQuestions.getQuestions().setParseAnswer(questionAnswer);
            }

        }
        return surveyQuestionDAO.findSurvey(survey);
    }




    @PostMapping(value = "/submitSurvey/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitSurvey(@RequestBody UsersSurvey usersSurvey) {

        System.out.println(usersSurvey.getUserSurveyTemplate().toString());

        if(authData.login(usersSurvey.getUsersList())==null)
            return new ResponseEntity<Object>("Вы должны сначала регистрироваться", HttpStatus.FORBIDDEN);
        else{
            Users users=usersDAO.loadUserByUsername(usersSurvey.getUsersList().getUsername());
            System.out.println("users "+users.toString());
            usersSurvey.getUsersList().setId_user(users.getId_user());
            List<UsersSurvey> userSurvey=usersSurveyDAO.findByUsername(users);
            if(usersSurvey.isAnonymous() && userSurvey.size()==0) {
                Calendar calendar = Calendar.getInstance();
                usersSurvey.setUserGUI(calendar.getTimeInMillis());
            }
            if(userSurvey.size()!=0)
                usersSurvey.setUserGUI(userSurvey.get(0).getUserGUI());
            if(!usersSurvey.isAnonymous())
                usersSurvey.setUserGUI(new Long(0));
            SurveyTemplate survey=surveyDAO.findSurveyByAllSurveyParameters(usersSurvey.getUserSurveyTemplate());
            System.out.println("surveyTemple "+survey);
            usersSurvey.getUserSurveyTemplate().setId(survey.getId());

            for(SurveyQuestion surveyQuestion: usersSurvey.getUserSurveyTemplate().getSurveyTemplateQuestions()){
                Question question=questionDAO.findQuestionByTextQuestion(surveyQuestion.getQuestions().getTextQuestion());
                surveyQuestion.getQuestions().setId(question.getId());
                surveyQuestion.getQuestions().setTextQuestion(question.getTextQuestion());
                surveyQuestion.getQuestions().setQuestionType(question.getQuestionType());

                usersSurvey.setUserQuestion(question);

                surveyQuestion.getQuestions().getParseAnswer().entrySet().forEach(entry->{
                   Answer answer=answerDAO.findAnswer(entry.getValue().getTextAnswer());
                   entry.getValue().setTextAnswer(answer.getTextAnswer());
                   entry.getValue().setId(answer.getId());
                    usersSurvey.setUserAnswer(answer);
                    System.out.println("questionId "+surveyQuestion.getQuestions().getId()+" answerId "+entry.getValue().getId());
                   //if(usersSurveyDAO.findSurveyByAllSurveyParameters(users,answer,question,surveyQuestion.getSurveyTemplates())==null)
                    UsersSurvey saveUserSurvey=new UsersSurvey();
                    saveUserSurvey.setUserGUI(usersSurvey.getUserGUI());
                    saveUserSurvey.setUserSurveyTemplate(usersSurvey.getUserSurveyTemplate());
                    saveUserSurvey.setUsersList(users);
                    saveUserSurvey.setUserAnswer(usersSurvey.getUserAnswer());
                    saveUserSurvey.setUserQuestion(usersSurvey.getUserQuestion());
                    usersSurveyDAO.saveUserSurvey(saveUserSurvey);
                });

                }


            }



        System.out.println(usersSurvey.getUserSurveyTemplate().getSurveyTemplateQuestions());
        return new ResponseEntity<>(usersSurvey,HttpStatus.OK);

    }

   @GetMapping(value = "/fill/survey/")
    public List<UsersSurvey> usersSurveyList(){
        return usersSurveyDAO.findAllUsersSurvey();
   }

   @GetMapping(value = "/findUserSurveyByGUID/{gui}")
    public ResponseEntity<Object> findUserSurveyByGUID(@PathVariable Long gui){

        List<UsersSurvey> usersSurveyList=usersSurveyDAO.findByGUI(gui);
       /* for(UsersSurvey usersSurvey: usersSurveyList) {
           SurveyTemplate survey=surveyDAO.findSurveyByAllSurveyParameters(usersSurvey.getUserSurveyTemplate());
            System.out.println(survey);
            String answerNumber="answer";
            int i=1;
            List<UsersSurvey> surveyQuestion=usersSurveyDAO.findUsersSurveyBySurveyName(usersSurvey.getUserSurveyTemplate());

            for(UsersSurvey userSurveys: surveyQuestion){
                Map<String, Answer> questionAnswer=new HashMap<>();
                user
                for(Answer answers:answer){
                    questionAnswer.put(answerNumber+(i++),answers.getAnswer());
                    userSurveys.getQuestions().setParseAnswer(questionAnswer);
                }

            }
        }*/

        return new ResponseEntity<>(usersSurveyList ,HttpStatus.OK);
   }



}
