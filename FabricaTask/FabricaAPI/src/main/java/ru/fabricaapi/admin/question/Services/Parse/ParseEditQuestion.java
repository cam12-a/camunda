package ru.fabricaapi.admin.question.Services.Parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ParseEditQuestion {

    @JsonProperty
    private ParseSurvey newSurvey;
    @JsonProperty
    private ParseSurvey currentSurvey;

}
