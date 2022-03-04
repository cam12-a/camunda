package ru.fabricaapi.admin.question.Services.Parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ParseSurvey {
    @JsonProperty
    private String surveyName;
    @JsonProperty
    private Date dateStart;
    @JsonProperty
    private Date dateEnd;
    @JsonProperty
    private String description;
    @JsonProperty
    List<String>  questions;
}
