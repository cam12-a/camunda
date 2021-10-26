package ru.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ParseStatusModel.StatusModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Mapping {
    @JsonProperty
    private StatusModel StatusModel=new StatusModel();
}
