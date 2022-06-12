package ru.maralays.mfa.service.QRCODE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class EncryptQRCodeText {

    private final String key= UUID.randomUUID().toString()+"AL@MarAlAlys#";
    private String initialMessage=null;
    private String encodingQRText=null;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public String encryptedQRCode(String message) {

        this.initialMessage=message+"$@#*"+key;
        encodingQRText=bCryptPasswordEncoder.encode(initialMessage);
        return encodingQRText;
    }


    public Boolean decryptedQRCode(String message){
        return bCryptPasswordEncoder.matches(message,encodingQRText);
    }


    public String getEncodingQRText() {
        return encodingQRText;
    }


    public String getInitialMessage() {
        return initialMessage;
    }
}
