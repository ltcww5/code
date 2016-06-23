package com.better517na.JavaServiceRouteHelper.thrift.thread;

import com.better517na.JavaServiceRouteHelper.business.Cache.ExceptionCheck;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.JavaServiceRouteHelper.thrift.heartBeat.ThriftHeartBeatService;
import com.better517na.JavaServiceRouteHelper.thrift.invoke.ThriftInvoker;

/**
 * @心跳检测
 * 
 * @author guangyin
 *
 */
public final class ThriftHeartBeatHelper {
    
    /**
     * @心跳检测方法.
     * 
     * @param serviceRouteInfo
     *            异常列表
     */
    public static void heartBeat(ServiceRouteInfoCache serviceRouteInfo) {
        if (invokes(ThriftHeartBeatService.Client.class, getHearBeatMethod(serviceRouteInfo), getHeartBeatUrl(serviceRouteInfo), String.class) != null) {
            // 从异常列表中排除
            ExceptionCheck.updateCheckList(serviceRouteInfo, 1);
        }
    }

    /**
     * @心跳检测.
     * 
     * @param serviceClass
     *            serviceClass
     * @param methodName
     *            方法名
     * @param serviceUrl
     *            url
     * @param returnType
     *            returnType
     * @param <T>
     *            泛型
     * @param <E>
     *            泛型
     * @return boolean
     */
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, String serviceUrl,
            Class<E> returnType) {
        try {
            return ThriftInvoker.invokes(serviceClass, methodName, serviceUrl, null);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * @获取目标服务heartbeat的url
     * 
     * @param serviceRouteInfo
     *            路由信息
     * @return url
     */
    private static String getHeartBeatUrl(ServiceRouteInfoCache serviceRouteInfo) {
        // 定义绑定类型、服务IP,服务端口，服务路径
        String bindingType = "";
        String serviceIP = "";
        int servicePort;

        // 拼接的地址
        String urlString = "";

        // 不为空则进行地址拼接
        if (serviceRouteInfo != null) {
            // 获取绑定类型、服务IP,服务端口，服务路径
            bindingType = serviceRouteInfo.getBindingType();
            serviceIP = serviceRouteInfo.getServiceIP();
            servicePort = serviceRouteInfo.getServicePort();
            // 如果端口不存在
            if (servicePort <= 0) {
                // 域名+SvcPath
                urlString = serviceIP.trim();
            } else {
                // BindingType://ServiceIP:ServicePort/SvcPath（服务名+客户端访问服务端的地址)
                // int类型端口转string
                String port = Integer.toString(servicePort);
                // 拼接地址
                urlString = bindingType.trim().toLowerCase() + "://" + serviceIP.trim() + ":"
                        + port.trim() + "/";
            }
        }

        return urlString;
    }

    /**
     * @获取心跳方法
     * 
     * @param serviceRouteInfo
     *            serviceRouteInfo
     * @return 心跳的方法名
     */
    private static String getHearBeatMethod(ServiceRouteInfoCache serviceRouteInfo) {
        // 判空处理
        if (serviceRouteInfo == null) {
            return "";
        }
        return "heartBeat";
    }

}
