package ru.maralays.mfa.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maralays.mfa.Entity.MobileDetectionEntity;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/mobile/")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "device detection", description = "this rest api defined the device trying to connect to service", termsOfService = "http://localhost:8080/api/mobile/", version = "1.0.1"))

public class MobileDetection  {

    @GetMapping(value = "detect/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MobileDetectionEntity> detect(Device device){

        String msg = null;
        if (device.isMobile()) {
            msg = "mobile";
        } else if (device.isTablet()) {
            msg = "tablet";
        } else {
            msg = "desktop";
        }
        MobileDetectionEntity mobileDetectionEntity=new MobileDetectionEntity();
        mobileDetectionEntity.setDevicePlatform(device.getDevicePlatform().name());
        mobileDetectionEntity.setDeviceType(msg);
        log.error("Restcontroller device"+mobileDetectionEntity.toString());
        return new ResponseEntity<>(mobileDetectionEntity, HttpStatus.OK);
    }



}
