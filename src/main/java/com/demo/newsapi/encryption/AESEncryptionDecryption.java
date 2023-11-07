package com.demo.newsapi.encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static  java.nio.charset.StandardCharsets.UTF_8;

/*
 *Provides encryption and decryption of a string based on the secretKey provided
 */
@Slf4j
public class AESEncryptionDecryption {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    /**
     * @param myKey passed secret key
     */
    public static void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error in prepareSecreteKey: " + e.getMessage());
        }
    }

    /**
     * @param strToEncrypt string to be encrypted
     * @param secret key for encrypting
     * @return
     */
    public static String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param strToDecrypt string to be decrypted
     * @param secret key for encrypting
     * @return
     */
    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
