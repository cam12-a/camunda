package ru.fabricaapi.admin.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//import ru.fabricaapi.admin.question.Repository.QuestionTypeRepository;

import ru.fabricaapi.admin.question.Services.AnswerDAO;
import ru.fabricaapi.admin.question.Services.Parse.ParseQuestion;
import ru.fabricaapi.admin.question.Services.QuestionAnswerDAO;
import ru.fabricaapi.admin.question.Services.QuestionDAO;
//import ru.fabricaapi.admin.question.Services.QuestionTypeDAO;
import ru.fabricaapi.admin.question.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/question/")
public class QuestionRestAPI extends Question {


    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    Question question;

    @Autowired
    QuestionAnswerDAO questionAnswerDAO;

    @Autowired
    QuestionDAO questionDAO;





    @PostMapping(value = "/addQuestion/",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String addQuestion(@RequestBody ParseQuestion questions){

        System.out.println("log "+questions.toString()+"\n");

        questions.getQuestionAnswer().entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue()+" "+entry.getValue().getTextAnswer().toString());

            //Создание нового объекта для каждого вопроса
            Question newQuestion=new Question();

            newQuestion.setTextQuestion(entry.getValue().getTextQuestion());
            newQuestion.setQuestionType(EnumValue(entry.getValue().getTextAnswer().size()));

            //Сохранения вопроса
            //questionDAO.insertQuestion(newQuestion);


            questionDAO.insertQuestion(newQuestion);

            for(String question_answer: entry.getValue().getTextAnswer()){
                Set<Answer> answerSet=new HashSet<>();
                Set<Question> questionSet=new HashSet<>();
                Answer newAnswer=new Answer();
                QuestionAnswer questionAnswer=new QuestionAnswer();
                newAnswer.setTextAnswer(question_answer);
                answerSet.add(newAnswer);
                questionAnswer.setQuestion(newQuestion);
                questionAnswer.setAnswer(newAnswer);

                answerDAO.insertAnswer(newAnswer);

                questionAnswerDAO.insertQuestionAnswer(questionAnswer);
                System.out.println(question_answer);

              }

        });

       return null;
    }

    @GetMapping(value = "/allQuestion/")
    public List<Question> getAllQuestion(){
        return questionDAO.findAllQuestions();


    }

    @GetMapping(value = "/question/answer/")
    public List<QuestionAnswer> getAllQuestionAnswer(){
        return questionAnswerDAO.findAllQuestionAnswer();
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
