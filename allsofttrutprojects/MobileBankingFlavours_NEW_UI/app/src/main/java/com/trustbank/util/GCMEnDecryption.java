package com.trustbank.util;

import com.trustbank.BuildConfig;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GCMEnDecryption {
    private static final int GCM_TAG_LENGTH = 16;
    public static byte[] iv =  BuildConfig.iv.getBytes();
    public static GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
    public static Cipher cipher;
    public static String encrypt(byte[] decode, byte[] bytes) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec secretKey = new SecretKeySpec(decode,BuildConfig.aes_key);
        cipher = Cipher.getInstance(BuildConfig.no_pkcs7_key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);
        byte[] encryptedBytes = cipher.doFinal(bytes);
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedText;
    }

    public static String decrypt(byte[] decode, String base64DecodedBytes) {
        String decryptedText="";
        try{
            SecretKeySpec secretKey = new SecretKeySpec(decode,BuildConfig.aes_key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(base64DecodedBytes));
            decryptedText = new String(decryptedBytes);
        }catch(Exception e){
            e.printStackTrace();
        }
        return decryptedText;
    }
}
