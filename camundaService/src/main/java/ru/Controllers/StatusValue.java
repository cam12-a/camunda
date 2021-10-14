package ru.Controllers;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import ru.models.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Component
public class StatusValue {

    @PostMapping(value="/status/")
    public void changeStatus(@RequestBody Mapping statusModel){
        //System.out.println(statusModel.toString());
    }
}
