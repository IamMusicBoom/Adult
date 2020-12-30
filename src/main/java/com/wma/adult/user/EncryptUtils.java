package com.wma.adult.user;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * create by wma
 * on 2020/9/15 0015
 */
public class EncryptUtils {
    /**
     * 加密
     **/
    public static String encrypt(String str) {
        try {
            DESKeySpec keySpec = new DESKeySpec(EncryptUtils.class.getSimpleName().getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encryptedPwd = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
            return encryptedPwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 解密
     **/
    public static String decrypt(String str) {
        try {
            DESKeySpec keySpec = new DESKeySpec(EncryptUtils.class.getSimpleName().getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);
            byte[] decoded = Base64.getDecoder().decode(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainTextPwdBytes = cipher.doFinal(decoded);
            return new String(plainTextPwdBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String md5(String string) {
        byte[] encodeBytes = null;
        try {
            encodeBytes = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException neverHappened) {
            throw new RuntimeException(neverHappened);
        } catch (UnsupportedEncodingException neverHappened) {
            throw new RuntimeException(neverHappened);
        }

        return toHexString(encodeBytes);
    }

    private static final char[] hexDigits =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String toHexString(byte[] bytes) {
        if (bytes == null) return "";
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(hexDigits[(b >> 4) & 0x0F]);
            hex.append(hexDigits[b & 0x0F]);
        }
        return hex.toString();
    }

    public static String generateToken(String account,String password,long time){
        StringBuilder sb = new StringBuilder();
        sb.append(EncryptUtils.md5(account))
                .append("-")
                .append(EncryptUtils.md5(password))
                .append("-")
                .append(EncryptUtils.md5(String.valueOf(time)));
        return sb.toString();
    }

}
