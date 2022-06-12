package ru.chat.security;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.util.Base64;


@NoArgsConstructor
@Slf4j
public class CreateEndToEndTunnel extends GenerateKeys {

    private String endPoint1;
    private String endPoint2;
    private Key exchangedKey;
    private KeyAgreement keyAgreement;
    private final String ALGO="AES";
    byte[] sharedSecret;

    public CreateEndToEndTunnel(String endPoint1, String endPoint2){
        this.endPoint1=endPoint1;
        this.endPoint2=endPoint2;

    }

    public void createTunnel(PrivateKey privateKey,PublicKey publicKey){
        exchangeKey(privateKey,publicKey);
        //setReceiverPublicKey(publicKey);
    }

    protected void exchangeKey(PrivateKey privateKey, PublicKey publicKey) {
        try {
            keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            sharedSecret = keyAgreement.generateSecret();
            //log.error("key "+sharedsecret.toString());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }


    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public Key generateKey() {
        return new SecretKeySpec(sharedSecret, "AES");
    }




}
