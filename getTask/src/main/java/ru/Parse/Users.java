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

public class Users {
    @JsonProperty(required = false)
    private String Id;
    @JsonProperty(required = false)
    private List<String> identity_type_in=new ArrayList<>();

}
