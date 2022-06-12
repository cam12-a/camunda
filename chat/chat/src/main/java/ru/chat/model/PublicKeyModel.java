package ru.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyModel {
    private byte[] encoded;
    private String format;
    private String algorithm;
}
