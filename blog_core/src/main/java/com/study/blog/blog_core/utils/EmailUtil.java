package com.study.blog.blog_core.utils;

/**
 * @ClassName EmailUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/9 09:41
 * @Version 1.0
 */
public class EmailUtil {

    private static final String REGEX_EMAIL = "[a-zA-Z0-9_]+@\\w+(\\.com|\\.cn){1}";

    private static final String REGEX_EMAIL_2 = "[a-zA-Z0-9]\\\\w+@[a-zA-Z0-9]+\\\\.(cn|com|com.cn|net|gov)+";

    public static boolean checkEmail(String email) {
        return email.matches(REGEX_EMAIL_2);
    }
}
