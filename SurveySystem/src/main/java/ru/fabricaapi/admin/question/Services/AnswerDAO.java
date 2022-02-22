package ru.fabricaapi.admin.question.Services;

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
            answerRepository.save(answer);
        }catch(Exception e){
            return "Произошла ошибку при добавлении ответа";
        }
        return null;
    }

    public String updateAnswer(Answer answer){
        Answer tempAnswer=answerRepository.findById(answer.getId()).orElse(null);
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

    public int findAnswer(Answer answer){
        List<Answer> answers=answerRepository.findAll();
        for(Answer findAnswer : answers){
            if(findAnswer.getAnswer().equals(answer.getAnswer()))
                return findAnswer.getId();
        }
        return  -1;
    }
}
