package com.better517na.JavaServiceRouteHelper.thrift.pool;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteHelper;

/**
 * @连接管理器
 * @author guangyin
 * @version 2016-3-16
 * @since JDK1.7
 */
public final class ConnectionPoolManager {
    /** @日志记录器 */
    public Logger logger = LoggerFactory.getLogger(ConnectionPoolManager.class);

    /**
     * @单例的连接管理器
     */
    private static ConnectionPoolManager connectionPoolManager;
    
    /**
     * @外部通过此方法可以获取对象
     * 
     * @return 实例
     */
    public static ConnectionPoolManager getInstance() {
        if (connectionPoolManager == null) {
            synchronized (ServiceRouteHelper.class) {
                if (connectionPoolManager == null) {
                    connectionPoolManager = new ConnectionPoolManager();
                }
            }
        }
        return connectionPoolManager;
    }

    /**
     * 构造函数.
     */
    private ConnectionPoolManager() {

    };

    /**
     * @连接池集合
     */
    private static Map<String, GenericConnectionPool> providerMap = new HashMap<String, GenericConnectionPool>();
    
    /**
     * 
     * @获取连接池
     * @param serviceIP 服务器ip
     * @param servicePort 服务器端口
     * @return 返回连接池提供者
     */
    public GenericConnectionPool getProvider(String serviceIP, int servicePort) {
        return providerMap.get(serviceIP + ":" + servicePort);
    }


    /**
    * @创建连接池
    * @param serviceIP 服务器ip
    * @param servicePort 服务器端口
    * @return 返回连接池
    */
    public static GenericConnectionPool creatPool(String serviceIP, int servicePort) {
        GenericConnectionPool connectionPool = new GenericConnectionPool(serviceIP,
                    servicePort);
        providerMap.put(serviceIP + ":" + servicePort, connectionPool);
        return connectionPool;
    }

    /**
     * @销毁连接池
     * @param serviceIP serviceIP
     * @param servicePort servicePort
     */
    public void destroyPool(String serviceIP, int servicePort) {
        providerMap.remove(serviceIP + ":" + servicePort);
    }
}
