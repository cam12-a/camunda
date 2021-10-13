package ru.ParseStatusModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusModelFields {
    @JsonProperty
    private String name;
    @JsonProperty
    private String notificationText;
    @JsonProperty
    private String notificationHeader;
    @JsonProperty
    private String authorName;
    @JsonProperty
    private String assignedTo;

}
