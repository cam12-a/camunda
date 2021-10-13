package ru.ParseStatusModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusModel {
    @JsonProperty
    private StatusModelFields approved;
    @JsonProperty
    private StatusModelFields cancelled;
    @JsonProperty
    private StatusModelFields executed;

}
