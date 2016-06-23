/*
 * 文件名：JsonUtil.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonUtil.java
 * 修改人：chunfeng
 * 修改时间：2015年4月13日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.util;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * . json相关的处理方法
 * 
 * @author chunfeng
 */
public class JsonUtil {
    /**
     * . 使用日期格式
     */
    // 过滤Class类型的对象
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").addSerializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            if (arg0 != null) {
                if ("class".equalsIgnoreCase(arg0.getSimpleName())) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes arg0) {
            return false;
        } }).create();

    /**
     * . 转对象
     * 
     * @param json
     *            json
     * @param cla
     *            cla
     * @param <T>
     *            类型
     * @return 结果
     */
    public static <T> T toObject(String json, Class<T> cla) {
        return gson.fromJson(json, cla);
    }

    /**
     * . 转化object
     * 
     * @param json
     *            json
     * @param type
     *            type
     * @param cla
     *            cla
     * @param <T>
     *            类型
     * @return 结果
     */
    public static <T> T toObject(String json, Type type, Class<T> cla) {
        return gson.fromJson(json, type);
    }

    /**
     * . 转成json
     * 
     * @param content
     *            对象
     * @return 结果
     */
    public static String toJson(Object content) {
        if (content == null) {
            return "";
        }
        return gson.toJson(content);
    }

}
