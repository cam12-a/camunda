package ru.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import reactor.core.publisher.Mono;
import ru.Services.GetPerson;
import ru.models.Person;
import ru.parse.Mapping;
import ru.parse.Results;


public class RenderPerson implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        GetPerson getPerson=new GetPerson();
        Person person=new Person();
        Mapping mapping=getPerson.PersonInfo();

       delegateExecution.setVariable("Firstname",getPerson.getFirst());
       delegateExecution.setVariable("Lastname",getPerson.getLast());
       delegateExecution.setVariable("Gender",getPerson.getGender());


    }
}
