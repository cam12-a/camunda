package ru.maralays.mfa.service.QRCODE;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
@Service
@NoArgsConstructor
public class GenerateQRCode extends EncryptQRCodeText {

    public BufferedImage generateQRCode(String qrText) throws Exception {
        QRCodeWriter qrCode= new QRCodeWriter();
        BitMatrix bitMatrix=
                qrCode.encode(qrText, BarcodeFormat.QR_CODE,300,300);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
