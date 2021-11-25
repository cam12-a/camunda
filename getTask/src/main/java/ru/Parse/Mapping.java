package ru.Parse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* Класс который позволяет мапин данных
*
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mapping {
    @JsonProperty(required = false)
    private Filters filters;
    @JsonProperty(required = false)
    private Parameters parameters;

}
