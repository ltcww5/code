package com.better517na.JavaServiceRouteHelper.thrift.pool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.better517na.JavaServiceRouteHelper.business.property.PropertiesManager;

/**
 * @连接提供者实现类
 * @author guangyin
 * @version 2016-3-16
 * @since JDK1.7
 */
public class GenericConnectionPool implements ConnectionPool {

    /**
     * @log日誌
     */
    public static Logger logger = LoggerFactory.getLogger(GenericConnectionPool.class);

    /** @连接超时配置 */
    private int conTimeOut;

    /** @可以从缓存池中分配对象的最大数量 */
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;

    /** @缓存池中最大空闲对象数量 */
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;

    /** @缓存池中最小空闲对象数量 */
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;

    /** @阻塞的最大数量 */
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;

    /** @从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
    private boolean testOnBorrow = false;

    /**
     * @归还对象前验证是否可用
     */
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;

    /**
     * @等待时验证是否可用
     */
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;

    /** @对象缓存池 */
    private ObjectPool objectPool = null;

    /**
     * @param serviceIP
     *            服务的IP地址
     * @param servicePort
     *            服务的端口
     */
    public GenericConnectionPool(String serviceIP, int servicePort) {
        Properties pro = new Properties();
        try {
            String configPath = PropertiesManager.getConfigPath(PropertiesManager.SERVICEROUTE_FILENAME);
            pro.load(new FileInputStream(configPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("config/ServiceRoute.properties 文件不存在");
        } catch (IOException e) {
            throw new RuntimeException("IO问题 读取ServiceRoute.properties文件出错！");
        }
        // 对象池
        objectPool = new GenericObjectPool();
        if (pro.get("thrift.connectionPool.maxActive") != null) {
            maxActive = Integer.parseInt(pro.get("thrift.connectionPool.maxActive").toString());
        }
        if (pro.get("thrift.connectionPool.maxIdle") != null) {
            maxIdle = Integer.parseInt(pro.get("thrift.connectionPool.maxIdle").toString());
        }
        if (pro.get("thrift.connectionPool.minIdle") != null) {
            minIdle = Integer.parseInt(pro.get("thrift.connectionPool.minIdle").toString());
        }
        if (pro.get("thrift.connectionPool.maxWait") != null) {
            maxWait = Long.parseLong(pro.get("thrift.connectionPool.maxWait").toString());
        }
        if (pro.get("thrift.connectionPool.testOnBorrow") != null) {
            testOnBorrow = "1".equals(pro.get("thrift.connectionPool.testOnBorrow").toString()) ? true : false;
        }
        if (pro.get("thrift.connectionPool.testOnReturn") != null) {
            testOnReturn = "1".toString().equals(pro.get("thrift.connectionPool.testOnReturn")) ? true : false;
        }
        if (pro.get("thrift.connectionPool.testWhileIdle") != null) {
            testWhileIdle = "1".equals(pro.get("thrift.connectionPool.testWhileIdle").toString()) ? true : false;
        }
        ((GenericObjectPool) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
        // ((GenericObjectPool) objectPool).setWhenExhaustedAction(whenExhaustedAction);
        // 设置factory
        if (pro.get("thrift.connectionPool.conTimeOut") != null) {
            conTimeOut = Integer.parseInt(pro.get("thrift.connectionPool.conTimeOut").toString());
            if (conTimeOut < 0) {
                throw new RuntimeException("conTimeOut超时时间只能大于或等于0");
            }
        }
        ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(serviceIP, servicePort, conTimeOut);
        objectPool.setFactory(thriftPoolableObjectFactory);
    }

    @Override
    public TSocket getConnection() throws Exception {
        try {
            TSocket socket = (TSocket) objectPool.borrowObject();
            return socket;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void returnCon(TSocket socket) throws Exception {
        objectPool.returnObject(socket);
    }

}
