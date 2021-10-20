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
    private String name;
    @JsonProperty
    private String notificationText;
    @JsonProperty
    private String notificationHeader;
    @JsonProperty
    private String authorName;
    @JsonProperty
    private String assignedTo;
    @JsonProperty
    private boolean trigger;
    @JsonProperty
    private String dateFrom;
    @JsonProperty
    private String dateTo;
    @JsonProperty(required = true)
    private String status;

}
