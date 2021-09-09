package ru.Delegates;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.Model.ApplicationData;

import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Named("sendDataToKafka")
public class sendDataToKafka implements JavaDelegate {
    @Autowired
    ApplicationData applicationData;

    @Autowired
    private KafkaTemplate<String, ApplicationData> kafkaTemplate;
    private static final String TOPIC="recruitment_app_json";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        applicationData.setApplicationGUI(delegateExecution.getVariable("applicationGUI").toString());
       // DateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        System.out.println(delegateExecution.getVariable("dateBirth").toString());
        DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        applicationData.setDateBirth(formatter1.parse((delegateExecution.getVariable("dateBirth").toString())));
        applicationData.setFirstName(delegateExecution.getVariable("firstName").toString());
        applicationData.setLastName(delegateExecution.getVariable("lastName").toString());
        applicationData.setJobPlace(delegateExecution.getVariable("jobPlace").toString());
        applicationData.setName(delegateExecution.getVariable("name").toString());
        applicationData.setStage(Integer.parseInt(delegateExecution.getVariable("stage").toString()));
        applicationData.setStatus(delegateExecution.getVariable("status").toString());
        kafkaTemplate.send(TOPIC, applicationData);


    }
}
