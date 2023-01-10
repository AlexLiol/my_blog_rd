package com.study.blog.blog_admin.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName JedisUtilTest
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/2 18:09
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JedisUtilTest {

    @Test
    public void TestSetObject() {
        String key = "abcdef";
        String value = "23456";
        String setResult = JedisUtil.setObject(key, value, 100);
        System.out.println(setResult);
        Object getResult = JedisUtil.getObject(key);
        Assert.assertEquals(value, getResult);
    }
}
