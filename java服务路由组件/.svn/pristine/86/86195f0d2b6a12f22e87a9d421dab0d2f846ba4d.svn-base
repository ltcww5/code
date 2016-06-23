package com.better517na.JavaServiceRouteHelper.thrift.helper;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.Cache.ServiceRouteCacheManager;
import com.better517na.JavaServiceRouteHelper.business.Cache.ThreadListCache;
import com.better517na.JavaServiceRouteHelper.business.thread.DowntimeThread;
import com.better517na.JavaServiceRouteHelper.business.thread.ServerRouteUpdate;
import com.better517na.JavaServiceRouteHelper.business.thread.WriteLogThreadPool;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteHelper;
import com.better517na.JavaServiceRouteHelper.thrift.invoke.ThriftServiceRouteHelperCore;
import com.better517na.JavaServiceRouteHelper.thrift.thread.ThriftHeartBeatThread;

/**
 * @thrift客户端调用入口
 * @author guangyin
 *
 */
public final class ThriftServiceRouteHelper {
    /**
     * @声明静态的单例对象的变量.
     */
    private static volatile ThriftServiceRouteHelper serviceRouteHelper;

    
    /**
     * @外部通过此方法可以获取对象
     * 
     * @return 实例
     */
    public static ThriftServiceRouteHelper getInstance() {
        if (serviceRouteHelper == null) {
            synchronized (ServiceRouteHelper.class) {
                if (serviceRouteHelper == null) {
                    serviceRouteHelper = new ThriftServiceRouteHelper();
                }
            }
        }
        return serviceRouteHelper;
    }

    /**
     * 构造函数.
     */
    private ThriftServiceRouteHelper() {
        // 宕机线程
        Thread downTime = new Thread(new DowntimeThread());
        downTime.setDaemon(true);
        downTime.start();
        // 心跳检测线程(Thrift)
        Thread thriftHeartBeat = new Thread(new ThriftHeartBeatThread());
        thriftHeartBeat.setDaemon(true);
        thriftHeartBeat.start();
        // 异步更新路由列表信息
        Thread routeUpdate = new Thread(new ServerRouteUpdate());
        routeUpdate.setDaemon(true);
        routeUpdate.start();
    };

    /**
     * @new 2015-10-20
     * 
     * @param <T> 泛型接口类
     * @param <E> 返回值类型
     * @param serviceClass 服务接口类
     * @param methodName methodName
     * @param param 业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, Object... param) {
        return invokes(serviceClass, methodName, null, null, param);
    }

    /**
     * @new 2015-10-20
     * 
     * @param <T> 泛型接口类
     * @param <E> 返回值类型
     * @param serviceClass 服务接口类
     * @param methodName methodName
     * @param addtion 附带参数
     * @param param 业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, AdditionalParam addtion, Object... param) {
        return invokes(serviceClass, methodName, null, addtion, param);
    }

    /**
     * @用于研发开发使用的方法，不通过服务路由，直接指定目标的wsdl  
     * 
     * @param <T> 泛型接口类
     * @param <E> 返回值类型
     * @param serviceClass 服务接口类
     * @param methodName 方法名
     * @param wsdlUrl 目标wsdl路径
     * @param param 业务参数
     * @return 结果
     */
    public <T, E> E invokesForTest(Class<T> serviceClass, String methodName, String wsdlUrl, Object... param) {
        if (!RouteCache.isDebug) {
            throw new IllegalStateException("debug模式下才能使用该方法测试");
        }
        return ThriftServiceRouteHelperCore.invokesForTest(serviceClass, methodName, wsdlUrl, param);
    }

    /**
     * 
     * @路由服务主方法兼容以前的
     * 
     * @param <T> 泛型接口类
     * @param <E> 返回值类型
     * @param cla 返回值的class类型
     * @param serviceClass 服务接口类
     * @param methodName 方法名
     * @param addtion 日志相关附加信息
     * @param param 业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, Class<E> cla, AdditionalParam addtion, Object... param) {
        return ThriftServiceRouteHelperCore.invokes(serviceClass, methodName, cla, addtion, param,
                null);
    }


    /**
     * @特别注意 该方法用语当不使用服务路由组件的时候调用 关闭之后 不能重启
     */
    public static synchronized void closeJavaService() {
        // 日志线程池
        WriteLogThreadPool.getBTaskThreadPool().shutdownPool();

        // ehcache
        ServiceRouteCacheManager.shutDownEhcache();

        // 退出所有的线程
        ThreadListCache.existAllThread();
    }
}
