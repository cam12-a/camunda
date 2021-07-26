package ru.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
// главный класс маппинга
// который содержит массив Results
// и класс Info
// которые берутся с https://randomuser.me/api
// все остальны классы это модель
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mapping {
    @JsonProperty
    private Results[] results;

    @Override
    public String toString() {
        return "Mapping{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
