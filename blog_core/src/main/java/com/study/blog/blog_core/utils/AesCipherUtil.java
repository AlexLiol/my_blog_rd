package com.study.blog.blog_core.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.study.blog.blog_model.exception.CustomUnauthorizedException;
import com.sun.crypto.provider.SunJCE;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AesCipherUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/7 19:32
 * @Version 1.0
 */
@Component
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AesCipherUtil {

    private static final String AES_CIPHER_ALGORITHM_NAME = "AES";
    private static final String SHA1PRNG_SECURE_RANDOM    = "SHA1PRNG";

    private String encryptAESKey;

    public static void main(String[] args) {
        String myAccount = "myAccount";
        String myPassword = "myPassword";
        AesCipherUtil aesCipherUtil = new AesCipherUtil(myAccount);
        String encryptStr = aesCipherUtil.encrypt(myPassword);
        System.out.println("encrypt:" + encryptStr);
        String decryptStr = aesCipherUtil.decrypt(encryptStr);
        System.out.println("decrypt:" + decryptStr);
    }

    public String encrypt(String str) {
        try {
            Security.addProvider(new SunJCE());
            // 实例化支持 AES 算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
            // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_CIPHER_ALGORITHM_NAME);
            // 将私钥encryptAESKey先Base64解密后转换为byte[]数组按128位初始化
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG_SECURE_RANDOM);
            secureRandom.setSeed(encryptAESKey.getBytes());
            keyGenerator.init(128, secureRandom);
            // SecretKey 负责保存对称密钥 生成密钥
            SecretKey desKey = keyGenerator.generateKey();
            // 生成Cipher对象,指定其支持的AES算法，Cipher负责完成加密或解密工作
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM_NAME);
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] src = str.getBytes();
            // 该字节数组负责保存加密结果
            byte[] cipherByte = cipher.doFinal(src);
            // 先将二进制转换成16进制，再返回Base64加密后的String
            return Base64ConvertUtil.encode(HexConvertUtil.parseByte2HexStr(cipherByte));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("getInstance()方法异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("getInstance()方法异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("初始化Cipher对象异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("加密异常，密钥有误:{}", e.getMessage());
            throw new CustomUnauthorizedException("加密异常，密钥有误:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("Base64加密异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("Base64加密异常:" + e.getMessage());
        }
    }

    public String decrypt(String str) {
        try {
            // 实例化支持 AES 算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
            // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_CIPHER_ALGORITHM_NAME);
            // 将私钥encryptAESKey先Base64解密后转换为byte[]数组按128位初始化
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG_SECURE_RANDOM);
            secureRandom.setSeed(encryptAESKey.getBytes());
            keyGenerator.init(128, secureRandom);
            // SecretKey 负责保存对称密钥 生成密钥
            SecretKey desKey = keyGenerator.generateKey();
            // 生成Cipher对象,指定其支持的AES算法，Cipher负责完成加密或解密工作
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM_NAME);
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            System.out.println(str);
            System.out.println(Base64ConvertUtil.decode(str));
            byte[] cipherByte = cipher.doFinal(HexConvertUtil.parseHex2Byte(Base64ConvertUtil.decode(str)));
            return new String(cipherByte);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("getInstance()方法异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("getInstance()方法异常:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("Base64解密异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("Base64解密异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("初始化Cipher对象异常:{}", e.getMessage());
            throw new CustomUnauthorizedException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("解密异常，密钥有误:{}", e.getMessage());
            throw new CustomUnauthorizedException("解密异常，密钥有误:" + e.getMessage());
        }
    }
}
