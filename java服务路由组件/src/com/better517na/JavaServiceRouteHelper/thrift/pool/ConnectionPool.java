package com.better517na.JavaServiceRouteHelper.thrift.pool;

import org.apache.thrift.transport.TSocket;

/**
 * @连接提供者
 * @author guangyin
 * @version 2016-3-16
 * @since JDK1.7
 */
public interface ConnectionPool {
    /**
     * @取链接池中的一个链接
     * 
     * @return 返回连接对象
     * @throws Exception Exception
     */
    public TSocket getConnection() throws Exception;

    /**
     * @交还链接
     * 
     * @param socket 连接对象
     * @throws Exception Exception
     */
    public void returnCon(TSocket socket) throws Exception;
}
