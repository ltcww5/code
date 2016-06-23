package com.better517na.JavaServiceRouteHelper.business.heartbeat;

import java.util.Date;

import com.better517na.HeartBeatService.IHeartBeatContract;
import com.better517na.JavaServiceRouteHelper.business.Cache.ExceptionCheck;
import com.better517na.JavaServiceRouteHelper.business.cxfInvoke.CxfInvoker;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * 心跳检测.
 * 
 * @author yishao
 *
 */
public final class HeartBeatHelper {
    /**
     * 心跳检测方法.
     * 
     * @param serviceRouteInfo
     *            异常列表
     */
    public static void hearBeat(ServiceRouteInfoCache serviceRouteInfo) {
        if (invokes(IHeartBeatContract.class, getHearBeatMethod(serviceRouteInfo), getHeartBeatUrl(serviceRouteInfo), getDateType(serviceRouteInfo)) != null) {
            // 从异常列表中排除
            ExceptionCheck.updateCheckList(serviceRouteInfo, 1);
        }
    }

    /**
     * 心跳检测.
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
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, String serviceUrl, Class<E> returnType) {
        try {
            return CxfInvoker.invokes(serviceClass, methodName, serviceUrl, null);
        } catch (Exception e) {
            System.out.println();
        }
        return null;
    }

    /**
     * 获取目标服务heartbeat的url.
     * 
     * @param serviceRouteInfo
     *            路由信息
     * @return url
     */
    public static String getHeartBeatUrl(ServiceRouteInfoCache serviceRouteInfo) {
        // 定义绑定类型、服务IP,服务端口，服务路径
        String bindingType = "";
        String serviceIP = "";
        String svcPath = "";
        int servicePort;

        // 拼接的地址
        String urlString = "";

        // 不为空则进行地址拼接
        if (serviceRouteInfo != null) {
            // 获取绑定类型、服务IP,服务端口，服务路径
            bindingType = serviceRouteInfo.getBindingType();
            serviceIP = serviceRouteInfo.getServiceIP();
            servicePort = serviceRouteInfo.getServicePort();

            String tempSvcPatch = serviceRouteInfo.getSvcPath();

            if (tempSvcPatch != null && !"".equals(tempSvcPatch)) {
                // java 服务
                if (judgeIsJavaService(serviceRouteInfo)) {
                    String[] paths = tempSvcPatch.split("/");
                    if (paths != null && paths.length >= 2) {
                        svcPath = tempSvcPatch.replace(paths[paths.length - 1], "") + "/heartBeat?wsdl";
                    } else {
                        svcPath = "/heartBeat?wsdl";
                    }
                } else {
                    // C#服务
                    svcPath = "JavaHeartBeatService.svc?wsdl";
                }
            } else {
                throw new RuntimeException("服务路由信息不全，请注意查看[tempSvcPatch]");
            }
            // 如果端口不存在
            if (servicePort <= 0) {
                // 域名+SvcPath
                urlString = serviceIP.trim() + "/" + svcPath.trim();
            } else {
                // BindingType://ServiceIP:ServicePort/SvcPath（服务名+客户端访问服务端的地址)
                // int类型端口转string
                String port = Integer.toString(servicePort);
                // 拼接地址
                urlString = bindingType.trim().toLowerCase() + "://" + serviceIP.trim() + ":" + port.trim() + "/" + svcPath.trim();
            }
        }

        return urlString;
    }

    /**
     * . 获取心跳方法
     * 
     * @param serviceRouteInfo
     *            serviceRouteInfo
     * @return 心跳的方法名
     */
    public static String getHearBeatMethod(ServiceRouteInfoCache serviceRouteInfo) {
        // 判空处理
        if (serviceRouteInfo == null) {
            return "";
        }

        if (judgeIsJavaService(serviceRouteInfo)) {
            return "heartBeatJava";
        } else {
            // C#服务
            return "heartBeatCsharp";
        }
    }

    /**
     * . 返回Date类型
     * 
     * @param serviceRouteInfo
     *            serviceRouteInfo
     * @return 类型
     */
    public static Class<?> getDateType(ServiceRouteInfoCache serviceRouteInfo) {
        // 判空处理
        if (serviceRouteInfo == null) {
            return null;
        }
        // java 服务
        if (judgeIsJavaService(serviceRouteInfo)) {
            return Date.class;
        } else {
            // C#服务
            return javax.xml.datatype.XMLGregorianCalendar.class;
        }
    }

    /**
     * . 判断是否为java服务
     * 
     * @param service
     *            服务
     * @return 是否为java服务
     */
    private static boolean judgeIsJavaService(ServiceRouteInfoCache service) {
        // 默认值
        boolean isJava = false;
        if (service == null) {
            return isJava;
        }
        String serviceType = service.getServiceType();
        // JavaWs JavaWeb JavaAPP
        if (serviceType != null && serviceType.toUpperCase().startsWith("JAVA")) {
            isJava = true;
        }

        return isJava;
    }
}
