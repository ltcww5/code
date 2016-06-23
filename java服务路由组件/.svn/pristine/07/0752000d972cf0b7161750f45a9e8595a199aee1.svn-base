package com.better517na.JavaServiceRouteHelper.business.Cache;

import java.io.Serializable;
import java.net.URL;

import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存处理类.
 * @author yishao
 */
public class ServiceRouteCacheManager implements Serializable {
    /**
     * 缓存配置文件中的路由cache name属性.
     */
    public static final String SERVICEROUTEINFOCACHE = "ServiceRouteInfoCache";

    /**
     * 缓存配置文件中的宕机cache name属性.
     */
    public static final String SERVICEDOWNTIMEINFOCACHE = "ServiceDowntimeInfoCache";

    /**
     * . 宕机列表的缓存key
     */
    public static final String DOWNTIME_SERVER_KEY = "down_time_server_key";

    /**
     * 宕机锁.
     */
    private static Object downtimeLock = new Object();

    /**
     * 路由锁.
     */
    private static Object routeLock = new Object();

    /**
     * 序列化.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取缓存配置文件url.
     */
    private static URL url;

    /**
     * CacheManager.
     */
    private static CacheManager cacheManager;

    /**
     * 路由缓存.
     */
    public static Cache cache;

    /**
     * 宕机缓存.
     */
    public static Cache downTimeCache;

    // 静态初始化
    static {
        url = ServiceRouteCacheManager.class.getClassLoader().getResource("com/better517na/serviceRoute/config/ehcache/ehcache.xml");
        //不使用单例模式
        cacheManager = new CacheManager(url);
        
        cache = cacheManager.getCache(SERVICEROUTEINFOCACHE);
        downTimeCache = cacheManager.getCache(SERVICEDOWNTIMEINFOCACHE);
    }

    /**
     * 设置路由缓存.
     * 
     * @param mapKey
     *            key
     * @param value
     *            value
     */
    public static void setServiceRouteCache(String mapKey, ServiceCacheModel<ServiceRouteInfoCache> value) {
        // 加锁存入缓存
        synchronized (routeLock) {
            // 添加元素
            Element element = new Element(mapKey, value);
            cache.put(element);
        }
    }

    /**
     * 获取路由缓存.
     * 
     * @param mapKey
     *            获取缓存的key
     * @return cache
     */
    @SuppressWarnings("unchecked")
    public static ServiceCacheModel<ServiceRouteInfoCache> getServiceRouteCache(String mapKey) {
        Element el = cache.get(mapKey);

        // 判空处理
        if (el == null) {
            return null;
        }

        return (ServiceCacheModel<ServiceRouteInfoCache>) el.getObjectValue();
    }

    /**
     * 设置宕机缓存.
     * 
     * @param value
     *            value
     * 
     */
    public static void setServiceDowntimeInfoCache(ServiceCacheModel<ServiceDowntimeInfo> value) {
        synchronized (downtimeLock) {
            // 添加元素
            Element element = new Element(DOWNTIME_SERVER_KEY, value);
            downTimeCache.put(element);
        }
    }

    /**
     * 获取宕机缓存.
     * 
     * @return 缓存的内容
     */
    @SuppressWarnings("unchecked")
    public static ServiceCacheModel<ServiceDowntimeInfo> getServiceDowntimeInfoCache() {
        Element el = downTimeCache.get(DOWNTIME_SERVER_KEY);

        // 判空缓存
        if (el == null) {
            return null;
        }

        return (ServiceCacheModel<ServiceDowntimeInfo>) el.getObjectValue();
    }

    /**
     * . 关闭ehche缓存池
     */
    public static synchronized void shutDownEhcache() {
        if (cacheManager != null) {
            cacheManager.shutdown();
        }
    }
}
