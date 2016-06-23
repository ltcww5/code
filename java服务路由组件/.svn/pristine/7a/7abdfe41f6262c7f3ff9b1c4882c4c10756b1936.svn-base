package com.better517na.JavaServiceRouteHelper.thrift.invoke;

import java.util.HashMap;
import java.util.Map;

/**
 * @工具上下文对象
 * @author guangyin
 *
 */
public final class ThriftUtils {

    /**
     * 构造函数.
     * 
     */
    private ThriftUtils() {
        throw new RuntimeException("工具类不允许实例化");
    }

    /**
     * 设置环境变量.
     * 
     * @param map
     *            参数map
     * @param isCascade
     *            级联
     */
    public static void set(Map<String, Object> map, boolean isCascade) {
        if (null == map || map.isEmpty()) {
            return;
        }
        if (isCascade) {
            ThriftContext.cascadeThreadLocal.set(map);
        } else {
            ThriftContext.cascadeThreadLocal.get().clear();
        }
        ThriftContext.threadLocal.set(map);
    }

    /**
     * 设置环境变量.
     * 
     * @param key
     *            key
     * @param value
     *            value
     * @param isCascade
     *            级联
     */
    public static void set(String key, Object value, boolean isCascade) {
        Map<String, Object> map = ThriftContext.threadLocal.get();
        Map<String, Object> map1 = ThriftContext.cascadeThreadLocal.get();
        if (null == map) {
            map = new HashMap<>();
            ThriftContext.threadLocal.set(map);
        }
        if (null == map1) {
            map1 = new HashMap<>();
            ThriftContext.cascadeThreadLocal.set(map1);
        }
        map.put(key, value);
        if (isCascade) {
            map1.put(key, value);
        } else {
            map1.remove(key);
        }
    }

    /**
     * 获取环境变量.
     * 
     * @return map
     */
    public static Map<String, Object> get() {
        Map<String, Object> cascadeMap = ThriftContext.cascadeThreadLocal.get();
        Map<String, Object> map = ThriftContext.threadLocal.get();
        if (null == cascadeMap) {
            cascadeMap = new HashMap<>();
        }
        if (null == map) {
            map = new HashMap<>();
        }
        cascadeMap.putAll(map);
        return cascadeMap;
    }

    /**
     * 获取环境参数.
     * 
     * @param key
     *            key
     * @return value
     */
    public static Object get(String key) {
        Map<String, Object> cascadeMap = ThriftContext.cascadeThreadLocal.get();
        Map<String, Object> map = ThriftContext.threadLocal.get();
        Object value = null;
        if (null != cascadeMap && cascadeMap.containsKey(key)) {
            value = cascadeMap.get(key);
        }
        if (null != map && map.containsKey(key)) {
            value = map.get(key);
        }
        return value;
    }

    /**
     * 清除环境变量.
     * 
     */
    public static void clear() {
        if (null != ThriftContext.threadLocal.get()) {
            ThriftContext.threadLocal.get().clear();
        }
        if (null != ThriftContext.cascadeThreadLocal.get()) {
            ThriftContext.cascadeThreadLocal.get().clear();
        }
    }
}
