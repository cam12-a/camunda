package ru.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import ru.Services.GetPerson;
import ru.models.Person;


public class RenderPerson implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        GetPerson getPerson=new GetPerson();
        Person person=new Person();
       String Response=getPerson.PersonInfo();
       getPerson.ParsePersonData(Response);
       delegateExecution.setVariable("Firstname",getPerson.getFirstname());
       delegateExecution.setVariable("Lastname",getPerson.getLastname());
       delegateExecution.setVariable("Gender",getPerson.getGender());
       System.out.println(person.toString());

    }
}
