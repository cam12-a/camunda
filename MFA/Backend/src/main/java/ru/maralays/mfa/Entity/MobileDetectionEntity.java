package ru.maralays.mfa.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileDetectionEntity {
    private String deviceType;
    private String devicePlatform="IOS";
    private Boolean tablet=true;
    private Boolean normal=true;
    private Boolean mobile=true;
}
