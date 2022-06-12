package ru.chat.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class EncryptDecryptMessage {

/*
    public static String encrypt(PublicKey publicKey, String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            System.out.println("No data to encrypt!");
            return plainText;
        }
        Cipher cipher = null;
        String encryptedString = "";
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object with public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Encrypting the plain text string
            byte[] encryptedText = cipher.doFinal(plainText.getBytes());

            // Encoding the encrypted text to Base64
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception caught while encrypting : " + ex);
        }

        return encryptedString;
    }



    public static String decrypt(PrivateKey privateKey, String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) {
            System.out.println("No data to decrypt!");
            return cipherText;
        }
        String decryptedString = "";
        Cipher cipher = null;
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object with private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decoding from Base64
            byte[] encryptedText = Base64.getDecoder().decode(cipherText.getBytes());

            // Decrypting to plain text
            decryptedString = new String(cipher.doFinal(encryptedText));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception caught while decrypting : " + ex);
        }
        return decryptedString;
    }

 */



    public String encrypt(String msg, Key key, String ALGO) {
        try {

            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String decrypt(String encryptedData, Key key, String ALGO) {
        try {

            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode((encryptedData));
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }
}
