package com.better517na.JavaServiceRouteHelper.thrift.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Thrift连接池工厂
 * @author guangyin
 * @version 2016-3-16
 * @since JDK1.7
 */
public class ThriftPoolableObjectFactory implements PoolableObjectFactory {
    /** @日志记录器 */
    public static Logger logger = LoggerFactory.getLogger(ThriftPoolableObjectFactory.class);

    /** @服务的IP */

    private String serviceIP;

    /** @服务的端口 */
    private int servicePort;

    /** @超时设置 */
    private int timeOut;

    /**
     * @构造方法
     * @param serviceIP
     *            服务器ip
     * @param servicePort
     *            服务器端口
     * @param timeOut
     *            超时时间
     */
    public ThriftPoolableObjectFactory(String serviceIP, int servicePort, int timeOut) {
        this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.timeOut = timeOut;
    }

    @Override
    public void destroyObject(Object arg0) throws Exception {
        if (arg0 instanceof TSocket) {
            TSocket socket = (TSocket) arg0;
            if (socket.isOpen()) {
                socket.close();
            }
        }
    }

    /**
     * 
     */
    @Override
    public Object makeObject() throws Exception {
        try {
            TTransport transport = new TSocket(this.serviceIP, this.servicePort, this.timeOut);
            transport.open();
            return transport;
        } catch (Exception e) {
            logger.error("error ThriftPoolableObjectFactory()", e);
            throw e;
        }
    }

    @Override
    public boolean validateObject(Object arg0) {
        try {
            if (arg0 instanceof TSocket) {
                TSocket thriftSocket = (TSocket) arg0;
                // if (thriftSocket.isOpen()) {
                // String os = System.getProperty("os.name");
                // if (os != null && !"Windows 7".equals(os)) { // 非win7 验证连接
                // // win7系统下，发送16次紧急消息会报错
                // thriftSocket.getSocket().sendUrgentData(0x00);
                // thriftSocket.flush();
                // }
                // }
                return thriftSocket.isOpen();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void passivateObject(Object arg0) throws Exception {
        // DO NOTHING
    }

    @Override
    public void activateObject(Object arg0) throws Exception {
        // DO NOTHING
    }

    /**
     * @serverIP
     * @return String
     */
    public String getServiceIP() {
        return serviceIP;
    }

    /**
     * @param serviceIP
     *            服务ip
     */
    public void setServiceIP(String serviceIP) {
        this.serviceIP = serviceIP;
    }

    /**
     * @获取端口
     * @return 端口
     */
    public int getServicePort() {
        return servicePort;
    }

    /**
     * @设置端口
     * @param servicePort
     *            servicePort
     */
    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    /**
     * @获取超时时间
     * @return 设置超时时间
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * @设置超时时间
     * @param timeOut
     *            超时时间
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
