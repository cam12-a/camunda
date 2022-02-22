package ru.fabricaapi.admin.question.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import ru.fabricaapi.admin.question.Repository.QuestionRepository;
import ru.fabricaapi.admin.question.model.Question;

import javax.transaction.TransactionalException;
import java.io.PrintStream;
import java.util.List;

@Service
public class QuestionDAO {
    @Autowired
    private QuestionRepository questionRepository;


    public String insertQuestion(Question questionModel){

        try{
            questionRepository.save(questionModel);
        }catch(Exception e){
            return e.getMessage();
        }

        return "Вопрос добавлен успешно";
    }

    public String editQuestion(Question questionModel, Long numberOfAnswer){

        Question tempQuestion=questionRepository.findById(questionModel.getId()).orElse(null);
        if(tempQuestion==null)
            return "Данный вопрос не существует добавьте пожалуйста его";


        return "Вопрос изменен успешно";
    }


   public int getIdQuestion(String textQuestion){
       List<Question> questions= questionRepository.findAll();
       for(Question findQuestion: questions){
           if(findQuestion.getTextQuestion().equals(textQuestion))
               return findQuestion.getId();
       }
       return -1;
   }
    public Question findQuestion(String textQuestion){
        List<Question> questions= questionRepository.findAll();
        for(Question findQuestion: questions){
            if(findQuestion.getTextQuestion().equals(textQuestion))
                return findQuestion;
        }
        return null;
    }

   public List<Question> findAllQuestions(){
        return questionRepository.findAll();
   }

   public Question findQuestionById(int id){
        return questionRepository.findById(id).orElse(null);
   }


}
