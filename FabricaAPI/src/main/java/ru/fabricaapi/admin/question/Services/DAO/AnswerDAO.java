package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.fabricaapi.admin.question.Repository.AnswerRepository;
import ru.fabricaapi.admin.question.model.Answer;

import java.util.List;


@Service
public class AnswerDAO {
    @Autowired
    AnswerRepository answerRepository;

    public String insertAnswer(Answer answer){
        try{
            if(findAnswer(answer.getTextAnswer())==null)
                answerRepository.save(answer);
            else
                return "Данный ответ уже существует";
        }catch(Exception e){
            return "Произошла ошибку при добавлении ответа";
        }
        return null;
    }

    public String updateAnswer(Answer answer){
        Answer tempAnswer=findAnswer(answer.getTextAnswer());
        if(tempAnswer==null)
            return "Данный ответ не существует, создайте пожалуйста вопрос и ответ";
        tempAnswer.setTextAnswer(answer.getTextAnswer());
        try{
            answerRepository.save(tempAnswer);
        }catch (Exception e){
            return "Произошла ошибка при изменении ответа";
        }
        return "Ответ успешно изменен";
    }

    public String deleteAnswer(Answer answer){
        try {
            if(findAnswer(answer.getTextAnswer())!=null)
                answerRepository.delete(answer);
        }catch (Exception e) {
            throw new RuntimeException("Произошла ошибку при удалении ответа");
        }
        return "Ответ успешно удален";
    }
    public String deleteAnswer(int id){
        Answer tempAnswer=answerRepository.findById(id).orElse(null);
        if(tempAnswer==null)
            return "Данный ответ не существует, создайте пожалуйста вопрос и ответ";

        try{
            answerRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Произошла ошибку при удалении ответа");
        }

        return "Ответ успешно удален";
    }

    public Answer findAnswer(String answer){
        return answerRepository.findByTextAnswer(answer);
    }
}
