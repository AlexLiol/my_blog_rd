package com.study.blog.blog_core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.study.blog.blog_model.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SerializableUtil
 * @Description 序列化工具类（也可使用protobuf）
 * @Author Alex Li
 * @Date 2022/10/2 17:03
 * @Version 1.0
 */
@Slf4j
public class SerializableUtil {

    /**
     * object 序列化
     */
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos  = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("SerializableUtil 序列化失败，出现 IOException:{}", e.getMessage());
            throw new CustomException("SerializableUtil 序列化失败，出现 IOException:" + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                log.error("SerializableUtil 序列化失败，出现 IOException:{}", e.getMessage());
                throw new CustomException("SerializableUtil 序列化失败，出现 IOException:" + e.getMessage());
            }
        }
    }

    /**
     * object 反序列化
     */
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois  = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            log.error("SerializableUtil 反序列化失败，出现 ClassNotFoundException:{}", e.getMessage());
            throw new CustomException("SerializableUtil 反序列化失败，出现 ClassNotFoundException:" + e.getMessage());
        } catch (IOException e) {
            log.error("SerializableUtil 反序列化失败，出现 IOException:{}", e.getMessage());
            throw new CustomException("SerializableUtil 反序列化失败，出现 IOException:" + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                log.error("SerializableUtil 反序列化失败，出现 IOException:{}", e.getMessage());
                throw new CustomException("SerializableUtil 反序列化失败，出现 IOException:" + e.getMessage());
            }
        }
    }
}
