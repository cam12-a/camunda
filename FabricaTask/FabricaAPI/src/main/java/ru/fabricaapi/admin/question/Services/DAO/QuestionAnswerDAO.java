package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.fabricaapi.admin.question.Repository.QuestionAnswerRepository;
import ru.fabricaapi.admin.question.model.Question;
import ru.fabricaapi.admin.question.model.QuestionAnswer;
import ru.fabricaapi.admin.question.model.SurveyQuestion;

import java.util.List;

@Service

public class QuestionAnswerDAO {
    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    public String insertQuestionAnswer(QuestionAnswer questionAnswer){

        try {
            questionAnswerRepository.save(questionAnswer);

        }catch (Exception e) {
            return "произошло ошибку при добавлении вопроса и ответа";
        }

        return "вопрос и ответ успешны добавлены";
    }


    public List<QuestionAnswer> findAllQuestionAnswer(){
        return questionAnswerRepository.findAll();
    }

    public List<QuestionAnswer> findAnswer(Question question){
        return questionAnswerRepository.findByQuestion(question);
    }


}
