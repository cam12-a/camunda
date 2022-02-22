package ru.fabricaapi.admin.question.Services.Parse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MapQuestionAnswer {
    private String textQuestion;
    private List<String> textAnswer;
}
