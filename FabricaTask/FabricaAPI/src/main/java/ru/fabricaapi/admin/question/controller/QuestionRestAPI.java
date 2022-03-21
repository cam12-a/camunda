package ru.fabricaapi.admin.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//import ru.fabricaapi.admin.question.Repository.QuestionTypeRepository;

import ru.fabricaapi.admin.question.Services.DAO.AnswerDAO;

import ru.fabricaapi.admin.question.Services.DAO.QuestionAnswerDAO;
import ru.fabricaapi.admin.question.Services.DAO.QuestionDAO;
//import ru.fabricaapi.admin.question.Services.QuestionTypeDAO;
import ru.fabricaapi.admin.question.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/question")
public class QuestionRestAPI extends Question {


    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    Question question;

    @Autowired
    QuestionAnswerDAO questionAnswerDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    Answer answer;




    @PostMapping(value = "/addQuestion/",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public Question addQuestion(@RequestBody Question questions){

        System.out.println("log "+questions.toString()+"\n"+questions);


        questions.setQuestionType(EnumValue(questions.getParseAnswer().values().size()));
        questionDAO.insertQuestion(questions);

        questions.getParseAnswer().entrySet().forEach(entry -> {
            System.out.println("key "+entry.getKey()+"value "+entry.getValue());
            QuestionAnswer questionAnswer=new QuestionAnswer();
            Question question=questionDAO.findQuestionByTextQuestion(questions.getTextQuestion());
            answerDAO.insertAnswer(entry.getValue());
            questionAnswer.setQuestion(question);
            Answer answer=answerDAO.findAnswer(entry.getValue().getTextAnswer());
            //System.out.println("questionId"+)
            questionAnswer.setAnswer(answer);
            questionAnswerDAO.insertQuestionAnswer(questionAnswer);

        });


        return questions;
    }



    @GetMapping(value = "/allQuestion/")
    public List<Question> getAllQuestion(){
        return questionDAO.findAllQuestions();

    }

    @GetMapping(value = "/question/answer/")
    public List<QuestionAnswer> getAllQuestionAnswer(){
        return questionAnswerDAO.findAllQuestionAnswer();
    }

    @PutMapping(value = "/edit/question/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Question editQuestion(@RequestBody Question questionAnswer){
        questionDAO.editQuestion(questionAnswer);
        //return questionDAO.findQuestion(questionAnswer.getTextQuestion());
        return questionDAO.findQuestionByTextQuestion(questionAnswer.getTextQuestion());
    }

    @GetMapping(value = "/{textQuestion}/")
    public Question getQuestion(@PathVariable String textQuestion){
        return questionDAO.findQuestionByTextQuestion(textQuestion);
    }


    public String  EnumValue(int answerSize){

        switch(answerSize){
            case 1: return QuestionTypeValue.ONE.label;
            case 2: return QuestionTypeValue.TWO.label;
            case 0: return QuestionTypeValue.NONE.label;
            default: return "Ошибка определения тип вопроса";
        }
    }

    public int EnumValue(String questionTypeName){
        if(questionTypeName.equals(QuestionTypeValue.NONE.label))
            return  1;
        if(questionTypeName.equals(QuestionTypeValue.NONE.label))
            return 0;
        if(questionTypeName.equals(QuestionTypeValue.TWO.label))
            return 2;
        return -1;
    }




}
