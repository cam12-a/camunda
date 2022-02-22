package ru.fabricaapi.admin.question.Services.Parse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseQuestion {
    @JsonProperty
    private Map<String,MapQuestionAnswer> questionAnswer;
}
