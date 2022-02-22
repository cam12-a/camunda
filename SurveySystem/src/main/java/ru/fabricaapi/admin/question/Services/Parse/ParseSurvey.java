package ru.fabricaapi.admin.question.Services.Parse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ParseSurvey {
    private String surveyName;
    private Date dateStart;
    private Date dateEnd;
    private String description;
    List<String>  questions;
}
