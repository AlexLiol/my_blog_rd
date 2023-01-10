package com.study.blog.blog_core.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @ClassName Base64ConvertUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 23:05
 * @Version 1.0
 */
public class Base64ConvertUtil {

    /**
     * base64 编码
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes());
        return new String(encodeBytes);
    }

    /**
     * base64 解码
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes());
        return new String(decodeBytes);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encodeResult = Base64ConvertUtil.encode("CTL^Ua2U%%sa7Ste");
        System.out.println(encodeResult);
        String decodeResult = Base64ConvertUtil.decode(encodeResult);
        System.out.println(decodeResult);
    }
}
