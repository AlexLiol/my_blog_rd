package com.study.blog.blog_core.utils;

/**
 * @ClassName StringUtil
 * @Description 字符串工具类
 * @Author Alex Li
 * @Date 2022/10/2 16:59
 * @Version 1.0
 */
public class StringUtil {

    private static final char UNDERLINE = '_';

    /**
     * byte 数组为空判断
     */
    public static boolean isNull(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * byte 数组不为空判断
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }
}
