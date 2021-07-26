package ru.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    @JsonProperty
    public String title = "";
    @JsonProperty
    public String first = "";
    @JsonProperty
    public String last = "";


}
