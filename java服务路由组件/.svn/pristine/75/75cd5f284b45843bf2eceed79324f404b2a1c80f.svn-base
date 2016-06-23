package com.better517na.JavaServiceRouteHelper.thrift.helper;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.event.Handler;
import com.better517na.JavaServiceRouteHelper.thrift.heartBeat.ThriftHeartBeatService;
import com.better517na.JavaServiceRouteHelper.thrift.heartBeat.impl.ThriftHeartBeatServiceImpl;
import com.better517na.JavaServiceRouteHelper.thrift.invoke.ThriftServiceHandler;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * @thrift服务注册中心
 * @author guangyin
 *
 */
public class ThriftServiceCenter implements Runnable, InitializingBean, DisposableBean {
    /**
     * @端口号
     */
    private int port;

    /**
     * @thrift 服务中心
     */
    private TServer server;

    /**
     * @线程集合
     */
    private Map<String, Object> processorMap = new HashMap<String, Object>();

    /**
     * 添加字段注释.
     */
    private Handler handler;

    /**
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return processorMap
     */
    public Map<String, Object> getProcessorMap() {
        return processorMap;
    }

    /**
     * @param processorMap
     *            processorMap
     */
    public void setProcessorMap(Map<String, Object> processorMap) {
        this.processorMap = processorMap;
    }

    /**
     * @程序启动代码
     */
    public void startServer() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        TServerTransport t;
        this.isPortValidate();
        try {
            t = new TServerSocket(port);
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            Set<String> set = processorMap.keySet();
            // 将心跳接口加入到发布列表
            TProcessor heartBeatPro = new ThriftHeartBeatService.Processor<ThriftHeartBeatService.Iface>(new ThriftHeartBeatServiceImpl());
            processor.registerProcessor("com.better517na.JavaServiceRouteHelper.thrift.heartBeat.ThriftHeartBeatService", heartBeatPro);
            // 将用户配置的接口加入到发布列表
            for (String interfaceName : set) {
                Object o = processorMap.get(interfaceName);
                // com.better517na.balAccDataService.service.Hello$Processor
                Class<?> proClass = Class.forName(interfaceName + "$Processor");
                Class<?> ifaceClass = Class.forName(interfaceName + "$Iface");
                TProcessor pro = (TProcessor) proClass.getConstructor(ifaceClass).newInstance(o);
                processor.registerProcessor(interfaceName, pro);
            }
            server = new TThreadPoolServer(new TThreadPoolServer.Args(t).processor(processor));
            server.setServerEventHandler(new ThriftServiceHandler(handler));
            System.out.println("the server is started and is listening at " + port + "...");
            server.serve();
        } catch (Exception e) {
            MLogException mLogException = new MLogException(ExceptionLevel.Error, "v6910", StringUtil.getExceptionDetailStr(e));
            LogOperate.writeExceptionLogSafe(mLogException);
            e.printStackTrace();
        }
    }

    /**
     * @验证端口是否可用
     */
    private void isPortValidate() {
        try {
            ServerSocket so = new ServerSocket(port);
            so.close();
        } catch (IOException e1) {
            RuntimeException e = new RuntimeException("thrift服务中心没有正常启动，请检查端口占用情况！");
            MLogException mLogException = new MLogException(ExceptionLevel.Error, "v6910", StringUtil.getExceptionDetailStr(e));
            LogOperate.writeExceptionLogSafe(mLogException);
            throw e;
        }
    }


    @Override
    public void destroy() throws Exception {
        server.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startServer();
    }

    /**
     * @设置handler
     * @return 返回handler
     */
    public Handler getHandler() {
        return handler;
    }
    /**
     * 获取handler.
     * @param handler 要设置的handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * @return server
     */
    public TServer getServer() {
        return server;
    }

    /**
     * @param server server 
     */
    public void setServer(TServer server) {
        this.server = server;
    }

}
