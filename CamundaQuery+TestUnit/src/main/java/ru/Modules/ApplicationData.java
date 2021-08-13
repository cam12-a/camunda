package ru.Modules;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.Date;


@Data

@NoArgsConstructor

public class ApplicationData {
    private Date vacationStartDate;
    private Date vacationEndDate;
    private String vacationType;
    private String  status;
    private String statusManager;
    private String applicationGUI;


}
