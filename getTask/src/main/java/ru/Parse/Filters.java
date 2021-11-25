package ru.Parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Filters {
    @JsonProperty(required = false)
    private List<String> business_key_in=new ArrayList<>();
    @JsonProperty(required = false)
    private List<String> task_definition_key_in=new ArrayList<>();
    @JsonProperty(required = false)
    private List<Users> users=new ArrayList<>();

}
