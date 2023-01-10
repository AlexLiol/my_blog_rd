package com.study.blog.blog_core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.study.blog.blog_model.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName PropertiesUtil
 * @Description 配置文件工具类
 * @Author Alex Li
 * @Date 2022/10/2 23:04
 * @Version 1.0
 */
@Slf4j
public class PropertiesUtil {

    /**
     * PROP
     */
    private static final Properties PROP = new Properties();

    /**
     * 加载配置文件
     */
    public static void readProperties(String fileName) {
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PROP.load(bf);
        } catch (IOException e) {
            log.error("PropertiesUtil 读取配置文件出现 IOException, fileName:{}, cause:{}", fileName, e.getMessage());
            throw new CustomException(
                "PropertiesUtil 读取配置文件出现 IOException, fileName:" + fileName + ", cause:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("PropertiesUtil 关闭资源时出现 IOException, fileName:{}, cause:{}", fileName, e.getMessage());
                    throw new CustomException(
                        "PropertiesUtil 关闭资源时出现 IOException, fileName:" + fileName + ", cause:" + e.getMessage());
                }
            }
        }
    }

    /**
     * 读取配置
     */
    public static String getProperty(String key) {
        return PROP.getProperty(key);
    }
}
