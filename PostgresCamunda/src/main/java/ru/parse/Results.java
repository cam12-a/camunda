package ru.parse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Results{
    @JsonProperty
    private Results[] results;
    @JsonProperty
    private String gender = "";
    @JsonProperty
    private Name name;

    @Override
    public String toString() {
        return "Results{" +
                "results=" + Arrays.toString(results) +
                ", gender='" + gender + '\'' +
                ", name=" + name +

                '}';
    }
}
