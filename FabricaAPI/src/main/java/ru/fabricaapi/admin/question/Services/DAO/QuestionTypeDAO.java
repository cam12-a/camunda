package ru.fabricaapi.admin.question.Services.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
//import ru.fabricaapi.admin.question.Repository.QuestionTypeRepository;
//import ru.fabricaapi.admin.question.model.Answer;
import ru.fabricaapi.admin.question.model.QuestionType;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

/*
@Service
public class QuestionTypeDAO {

    @Autowired
    QuestionTypeRepository questionTypeRepository;

    public int findByQuestionTypeName (String questionTypeName){
        List<QuestionType>  allQuestionTypes =questionTypeRepository.findAll();
        for(int i=0;i<allQuestionTypes.size();i++){
            if(allQuestionTypes.get(i).getQuestionTypeName().equals(questionTypeName))
                return allQuestionTypes.get(i).getId();
        }
        return 0;
    }

    public void saveQuestionType(QuestionType questionType){
       try{
           questionTypeRepository.save(questionType);
       }catch(UnexpectedRollbackException e){
            //System.out.println(e.getMessage());
       }
    }

    public void saveQuestionType(int id, QuestionType questionType,String questionTypeName){
        QuestionType tempQuestionType = questionTypeRepository.findById(id).orElse(null);
        if(tempQuestionType!=null){
            if(questionType.getQuestionTypeName().equals(questionTypeName))
            tempQuestionType.setQuestionTypeName(questionType.getQuestionTypeName());
            questionTypeRepository.save(tempQuestionType);
        }
    }


}
*/