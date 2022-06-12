package ru.maralays.mfa.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maralays.mfa.Entity.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private String message;
    private Users user;
}
