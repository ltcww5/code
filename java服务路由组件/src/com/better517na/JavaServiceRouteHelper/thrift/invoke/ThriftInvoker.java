package com.better517na.JavaServiceRouteHelper.thrift.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.springframework.context.ApplicationContext;

import com.better517na.JavaServiceRouteHelper.business.property.PropertiesManager;
import com.better517na.JavaServiceRouteHelper.event.Handler;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.thrift.pool.ConnectionPool;
import com.better517na.JavaServiceRouteHelper.thrift.pool.ConnectionPoolManager;
import com.better517na.JavaServiceRouteHelper.util.ContextUtils;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * @实现thrift的远程调用实现
 * @author guangyin
 */
public class ThriftInvoker {

    /**
     * 添加字段注释.
     */
    private static Handler handler;

    /**
     * @路由服务主方法.
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            服务接口类 如：com.better517na.xxService.Iface.Client
     * @param methodName
     *            方法名
     * @param serviceUrl
     *            调用的服务 如:thrift://192.168.1.1:9090/hello
     * @param param
     *            业务参数
     * @return 结果
     * @throws Exception
     *             Exception
     */
    @SuppressWarnings("unchecked")
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, String serviceUrl, Object[] param) throws Exception {
        ConnectionPool connectionPool = null;
        TSocket transport = null; // 连接
        TBinaryProtocol protocol = null;
        TProtocol processor = null;
        Class<?>[] paramClass = null;
        if (param != null) {
            paramClass = new Class[param.length];
            for (int i = 0; i < param.length; i++) {
                paramClass[i] = changeType(param[i]);
                // paramClass[i] = .getClass();
            }
        } else {
            param = new Object[0];
        }
        if (StringUtil.isNullOrEmpty(serviceUrl)) {
            throw new InvalidParameterException("服务地址serviceUrl不能为空");
        }
        String[] split = serviceUrl.split("//")[1].split(":");
        String host = split[0]; // 192.168.1.1
        // 去svc
        int port = Integer.valueOf(split[1].substring(0, split[1].indexOf("/"))); // 9090
        // 去Client
        String serviceName = serviceClass.getName().substring(0, serviceClass.getName().indexOf("$"));

        // 判断连接池中是否有该连接
        connectionPool = ConnectionPoolManager.getInstance().getProvider(host, port);
        if (connectionPool == null) {
            connectionPool = ConnectionPoolManager.creatPool(host, port);
        }
        // System.out.println("线程准备获取连接" + Thread.currentThread().getId());
        transport = connectionPool.getConnection(); // 从连接池获取连接
        // System.out.println("线程准备已获取连接" + Thread.currentThread().getId());
        protocol = new TBinaryProtocol(transport);
        processor = new TMultiplexedProtocol(protocol, serviceName);
        Object result = null;
        Handler invokeHandler = null;
        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            // System.out.println("连接内存地址" + transport.toString());
            // guokan 2016-04-26
            invokeHandler = createHandler();
            if (null != invokeHandler) {
                invokeHandler.invokeBefore();
            }
            // 设置隐式提交参数
            // 非级联传递参数
            Map<String, Object> map = ThriftContext.threadLocal.get();
            if (null == map) {
                map = new HashMap<String, Object>(0);
            }
            byte[] b = ThriftServiceHandler.objToArr(map);
            transport.write(ThriftServiceHandler.int2byte(b.length));
            transport.write(b);

            // 级联传递参数
            map = ThriftContext.cascadeThreadLocal.get();
            if (null == map) {
                map = new HashMap<String, Object>(0);
            }
            if (ServiceRouteAttachHelper.getInstance().getTrackIDInfo() != null) {
                map.putAll(ServiceRouteAttachHelper.getInstance().getTrackIDInfo()); // 设置trackid
            }
            b = ThriftServiceHandler.objToArr(map);
            transport.write(ThriftServiceHandler.int2byte(b.length));
            transport.write(b);
            // 反射代理类
            Constructor<T> constructor = serviceClass.getConstructor(TProtocol.class);
            T newInstance = constructor.newInstance(processor);
            Method method = serviceClass.getMethod(methodName, paramClass);
            result = method.invoke(newInstance, param);
            // guokan 2016-04-26
            if (null != invokeHandler) {
                invokeHandler.invokeAfter();
            }
        } catch (Exception e) {
            transport.close();
            throw e;
        } finally {
            if (connectionPool != null && transport != null) {
                connectionPool.returnCon(transport); // 将连接放回到连接池
            }
        }
        if (result == null) {
            return null;
        }
        return (E) result;
    }

    /**
     * 获取handler.
     * 
     * @return Handler
     * @throws Exception
     *             e
     */
    public static Handler createHandler() throws Exception {
        // 2016-04-26 guokan 调用前处理
        if (null == handler) {
            ApplicationContext context = ContextUtils.getContext();
            String handlerClassName = PropertiesManager.handlerClassName;
            if (null != handlerClassName && !"".equals(handlerClassName.trim())) {
                if (null != context) {
                    handler = context.getBean(Handler.class);
                }
                if (null == handler) {
                    handler = (Handler) Class.forName(handlerClassName).newInstance();
                }
            }
        }
        return handler;
    }

    /**
     * @包装类类模板转基本数据类型类模板
     * @param obj
     *            包装类型
     * @return 基本数据类型类模板
     */
    private static Class<?> changeType(Object obj) {
        if (obj instanceof Integer) {
            return Integer.TYPE;
        } else if (obj instanceof Boolean) {
            return Boolean.TYPE;
        } else if (obj instanceof Long) {
            return Long.TYPE;
        } else if (obj instanceof Double) {
            return Double.TYPE;
        } else if (obj instanceof Byte) {
            return Byte.TYPE;
        } else if (obj instanceof Short) {
            return Short.TYPE;
        } else {
            return obj.getClass();
        }

    }

}
