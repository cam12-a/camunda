package ru.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ApplicationData {
    private String applicationGUI;
    private String firstName;
    private String name;
    private String lastName;
    private String dateBirth;
    private int stage;
    private String status;
    private String jobPlace;

}
