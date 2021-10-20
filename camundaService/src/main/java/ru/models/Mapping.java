package ru.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ParseStatusModel.StatusModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mapping {
    @JsonProperty
    private StatusModel StatusModel;
}
