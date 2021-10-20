package ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicationData {
    private Date dateFrom;
    private  Date dateTo;
    private String reason;
    private String status;
    private boolean parallelWay;
    private String submittedBy;
    boolean performedByManager;
    private Mapping mapping;
}
