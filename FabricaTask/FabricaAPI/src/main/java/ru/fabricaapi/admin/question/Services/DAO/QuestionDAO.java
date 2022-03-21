package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import ru.fabricaapi.admin.question.Repository.QuestionRepository;
import ru.fabricaapi.admin.question.model.Question;

import javax.transaction.TransactionalException;
import java.io.PrintStream;
import java.util.Arrays;
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

    public String editQuestion(Question question){

        Question tempQuestion=findQuestionByTextQuestion(question.getOldTextQuestion());
        if(tempQuestion==null) {
            System.out.println("Данный вопрос не существует добавьте пожалуйста его");
            return "Данный вопрос не существует добавьте пожалуйста его";
        }

        tempQuestion.setTextQuestion(question.getTextQuestion());
        questionRepository.save(tempQuestion);
        System.out.println("Вопрос изменен успешно");
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
    public Question findQuestionByTextQuestion(String textQuestion){
        return questionRepository.findByTextQuestion(textQuestion);
    }

   public List<Question> findAllQuestions(){
        return questionRepository.findAll();
   }

   public Question findQuestionById(int id){
        return questionRepository.findById(id).orElse(null);
   }


}
