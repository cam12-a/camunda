package ru.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notifications {
    private String notificationHeader;
    private String notificationText;
    private String receiverId;
}