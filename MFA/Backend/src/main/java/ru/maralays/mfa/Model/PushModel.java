package ru.maralays.mfa.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushModel {

    private String to;
    @JsonProperty("notification")
    private Notifications notification;
}
