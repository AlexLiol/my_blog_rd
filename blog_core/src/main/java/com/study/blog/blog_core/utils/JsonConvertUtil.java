package com.study.blog.blog_core.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName JsonConvertUtil
 * @Description json 转换工具类,Json和Object的互相转换，转List必须Json最外层加[]，转Object，Json最外层不要加[]
 * @Author Alex Li
 * @Date 2022/10/1 22:51
 * @Version 1.0
 */
public class JsonConvertUtil {

    /**
     * json 转 Object
     */
    public static <T> T json2Object(String pojo, Class<T> clazz) {
        return JSONObject.parseObject(pojo, clazz);
    }

    public static <T> String object2Json(T t) {
        return JSONObject.toJSONString(t);
    }
}
