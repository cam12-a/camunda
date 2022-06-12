package ru.maralays.mfa.service.QRCODE;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SendQRCode {

    @Autowired
    private EncryptQRCodeText encryptQRCodeText;
    @Autowired
    private GenerateQRCode generateQRCode;

    public BufferedImage SendQRCode(String message) throws Exception {
        return generateQRCode.generateQRCode(encryptQRCodeText.encryptedQRCode(message));
    }


}
