package ru.Controllers;


import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.Parse.Mapping;
import ru.Services.TaskDetails;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
public class TasksID {

    @Autowired
    TaskDetails taskDetails;

    @GetMapping(value = "/returnIdTask/")
    public List<String> idTask(@RequestBody Mapping mapping)
    {
        taskDetails.getTasksID(mapping);
        return new ArrayList<>(new LinkedHashSet<>(taskDetails.getTasksID()));
    }
}
