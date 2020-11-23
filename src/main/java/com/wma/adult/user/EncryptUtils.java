package com.wma.adult.user;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
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

}
