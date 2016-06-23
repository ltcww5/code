package com.better517na.JavaServiceRouteHelper.serviceRouteHelper;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.Cache.ServiceRouteCacheManager;
import com.better517na.JavaServiceRouteHelper.business.Cache.ThreadListCache;
import com.better517na.JavaServiceRouteHelper.business.serviceRouteHelper.ServiceRouteHelperCore;
import com.better517na.JavaServiceRouteHelper.business.thread.DowntimeThread;
import com.better517na.JavaServiceRouteHelper.business.thread.HeartBeatThread;
import com.better517na.JavaServiceRouteHelper.business.thread.ServerRouteUpdate;
import com.better517na.JavaServiceRouteHelper.business.thread.WriteLogThreadPool;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;

/**
 * 路由服务入口.
 * 
 * @author yishao
 *
 */
public final class ServiceRouteHelper {
    /**
     * 声明静态的单例对象的变量.
     */
    private static volatile ServiceRouteHelper serviceRouteHelper;

    /**
     * 外部通过此方法可以获取对象.
     * 
     * @return 实例
     */
    public static ServiceRouteHelper getInstance() {
        if (serviceRouteHelper == null) {
            synchronized (ServiceRouteHelper.class) {
                if (serviceRouteHelper == null) {
                    serviceRouteHelper = new ServiceRouteHelper();
                }
            }
        }
        return serviceRouteHelper;
    }

    /**
     * 构造函数.
     */
    private ServiceRouteHelper() {
        // 宕机线程
        Thread downTime = new Thread(new DowntimeThread());
        downTime.setDaemon(true);
        downTime.start();
        // 心跳检测线程
        Thread heartBeat = new Thread(new HeartBeatThread());
        heartBeat.setDaemon(true);
        heartBeat.start();
        // 异步更新路由列表信息
        Thread routeUpdate = new Thread(new ServerRouteUpdate());
        routeUpdate.setDaemon(true);
        routeUpdate.start();
    };

    /**
     * . new 2015-10-20
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            服务接口类
     * @param methodName
     *            methodName
     * @param param
     *            业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, Object... param) {
        return invokes(serviceClass, methodName, null, null, param);
    }

    /**
     * . new 2015-10-20
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            服务接口类
     * @param methodName
     *            methodName
     * @param addtion
     *            附带参数
     * @param param
     *            业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, AdditionalParam addtion, Object... param) {
        return invokes(serviceClass, methodName, null, addtion, param);
    }

    /**
     * . 用于研发开发使用的方法，不通过服务路由，直接指定目标的wsdl .new 2015-10-26
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            服务接口类
     * @param methodName
     *            方法名
     * @param wsdlUrl
     *            目标wsdl路径
     * @param param
     *            业务参数
     * @return 结果
     */
    public <T, E> E invokesForTest(Class<T> serviceClass, String methodName, String wsdlUrl, Object... param) {
        if (!RouteCache.isDebug) {
            throw new IllegalStateException("debug模式下才能使用该方法测试");
        }
        return ServiceRouteHelperCore.invokesForTest(serviceClass, methodName, wsdlUrl, param);
    }

    /**
     * 
     * 路由服务主方法. 兼容以前的
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param cla
     *            返回值的class类型
     * @param serviceClass
     *            服务接口类
     * @param methodName
     *            方法名
     * @param addtion
     *            日志相关附加信息
     * @param param
     *            业务参数
     * @return 结果
     */
    public <T, E> E invokes(Class<T> serviceClass, String methodName, Class<E> cla, AdditionalParam addtion, Object... param) {
        return ServiceRouteHelperCore.invokes(serviceClass, methodName, cla, addtion, param, null);
    }

    /**
     * . 服务路由调用，带参数类型
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            契约class
     * @param methodName
     *            调用的方法名
     * @param cla
     *            返回参数类型
     * @param addtion
     *            记录日志的addtional
     * @param parameters
     *            入参参数
     * @param parameterTypes
     *            入参参数类型
     * @return 调用结果
     */
    @Deprecated
    public <T, E> E invokes(Class<T> serviceClass, String methodName, Class<E> cla, AdditionalParam addtion, Object[] parameters, Class<?>[] parameterTypes) {
        return ServiceRouteHelperCore.invokes(serviceClass, methodName, cla, addtion, parameters, parameterTypes);
    }

    /**
     * . 特别注意 该方法用语当不使用服务路由组件的时候调用 关闭之后 不能重启
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
