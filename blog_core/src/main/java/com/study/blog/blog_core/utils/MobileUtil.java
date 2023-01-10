package com.study.blog.blog_core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName MobileUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/9 09:42
 * @Version 1.0
 */
public class MobileUtil {

    private static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    private static final Integer MOBILE_LENGTH = 11;

    public static boolean checkMobile(String mobile) {
        boolean b;
        if (mobile.length() != MOBILE_LENGTH) {
            b = false;
        } else {
            Pattern p = Pattern.compile(REGEX_MOBILE);
            Matcher m = p.matcher(mobile);
            b = m.matches();
        }
        return b;
    }
}
