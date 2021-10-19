package ru.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Services.SetStatusValue;
import ru.models.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Component
public class StatusValue {

    @Autowired
    SetStatusValue setStatusValue;

    @PostMapping(value="/status/")
    public void changeStatus(@RequestBody Mapping statusModel){

        setStatusValue.statusUpdateByHrOrHrAssistant("Approved");
        System.out.println(statusModel.toString());
    }
}
