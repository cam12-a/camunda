package ru.Controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.models.MappeOperatorAssistant;
import ru.models.OperatorAssistant;
import ru.models.OperatorId;

import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class AddOperatorAssistant {
    @Autowired
    OperatorAssistant operatorAssistant;

    @PostMapping(value = "/addOperators/")
    public MappeOperatorAssistant addOperatorAssistant(@RequestBody MappeOperatorAssistant operatorAssistant){

        System.out.println(operatorAssistant.toString());
       return operatorAssistant;
    }

}
