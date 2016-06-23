/*
 * 文件名：ServiceRouteAttachHelper.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ServiceRouteAttachHelper.java
 * 修改人：chunfeng
 * 修改时间：2015年4月13日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.serviceRouteHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * . TODO 用于处理cxf传输过程中attach的读和写
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author chunfeng
 */
public final class ServiceRouteAttachHelper {

    /**
     * . 用于存储调用入口的附加信息
     */
    public static final ThreadLocal<Map<String, String>> THREAD_ATTACH_IN = new ThreadLocal<Map<String, String>>();

    /**
     * . 调用出口的附加信息
     */
    public static final ThreadLocal<Map<String, String>> THREAD_ATTACH_OUT = new ThreadLocal<Map<String, String>>();

    /**
     * . 附带值的key
     */
    public static final String ATTACH_KEY_VAL = "serviceRouteHelper_attach_key";

    /**
     * . element的name
     */
    public static final String ATTACH_ELEMENT_KEY_VAL = "serviceRouteHelper_attach_key";

    /**
     * . 对象
     */
    private static ServiceRouteAttachHelper attachHelper;

    /**
     * . 构造函数.
     */
    private ServiceRouteAttachHelper() {
    }

    /**
     * TODO 外部通过此方法可以获取对象.
     * 
     * @return 实例
     */
    public static ServiceRouteAttachHelper getInstance() {
        if (attachHelper == null) {
            synchronized (ServiceRouteHelper.class) {
                if (attachHelper == null) {
                    attachHelper = new ServiceRouteAttachHelper();
                }
            }
        }
        return attachHelper;
    }

    /**
     * .
     * 
     * TODO 从附带的map里面获取值
     * 
     * @param key
     *            key
     * @return 结果
     */
    public String getAttachVal(String key) {
        Map<String, String> valMap = THREAD_ATTACH_OUT.get();
        if (valMap == null || !valMap.keySet().contains(key)) {
            return null;
        }
        return valMap.get(key);
    }

    /**
     * . TODO 从附加值的map中获取值
     * 
     * @param key
     *            key
     * @param val
     *            val
     */
    public void setAttachVal(String key, String val) {
        Map<String, String> valMap = THREAD_ATTACH_IN.get();
        // 判空处理
        if (valMap == null) {
            valMap = new HashMap<String, String>();
            THREAD_ATTACH_IN.set(valMap);
        }
        valMap.put(key, val);
    }

    /**
     * @返回trackid的信息
     * @return map
     */
    public Map<String, String> getTrackIDInfo() {
        return THREAD_ATTACH_IN.get();
    }
}
