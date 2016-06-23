package com.better517na.JavaServiceRouteHelper.thrift.invoke;

import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.SocketException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;

import com.better517na.JavaServiceRouteHelper.business.Cache.ExceptionCheck;
import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.cxfInvoke.InvokeProcess;
import com.better517na.JavaServiceRouteHelper.business.impl.PollingMathImpl;
import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * @路由功能实现，主要用于找IP端口.
 * 
 * @author guangyin
 *
 */
public class ThriftServiceRouteHelperCore {
    /**
     * . 日志
     */
    private static Logger log = Logger.getLogger(ThriftServiceRouteHelperCore.class);

    /**
     * @服务路由调用，带参数类型
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
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, Class<E> cla,
            AdditionalParam addtion, Object[] parameters, Class<?>[] parameterTypes) {
        if (parameters == null) {
            parameters = new Object[0];
        }


        // 传入了类型参数 则需要验证
        if (parameterTypes != null) {
            if (parameters.length != parameterTypes.length) {
                throw new InvalidParameterException("入参参数与入参类型数量不匹配");
            }
        }
        // 轮询 结果
        ServiceRouteInfoCache serviceRouteInfo = null;
        // 调用的路由
        String serviceUrl = "";
        // 服务返回结果
        E result = null;
        try {
            // 获取服务路由列表
            List<ServiceRouteInfoCache> serviceAddressList = RouteCache.getRouteCache(serviceClass);
            if (RouteCache.isDebug) {
                System.out.println("count:" + serviceAddressList == null ? 0 : serviceAddressList.size());
            }
            // 防御性判空提示
            if (serviceAddressList == null || serviceAddressList.size() == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("契约:" + serviceClass.getName());
                sb.append(",机房:" + RouteCache.nanoValue);
                sb.append(",机器代码:" + RouteCache.dtctValue);
                sb.append(",唯一标识:" + RouteCache.uniqueSign);
                sb.append(",IsValid: 1 ");
                throw new RuntimeException("服务路由列表中无匹配，" + sb.toString());
            }

            // 轮询
            serviceRouteInfo = PollingMathImpl.instance.getServiceRouteMath(serviceAddressList, serviceClass.getName());

            // 获取服务地址
            serviceUrl = RouteCache.getServiceUrl(serviceRouteInfo);
            if (RouteCache.isDebug) {
                System.out.println("serviceUrl:" + serviceUrl);
            }

            long invokeMilles = 0;
            try {
                // 调用前的处理
                InvokeProcess.beforeInvokeProcess(addtion, methodName);
                // 调用一次 写一次日志
                invokeMilles = new Date().getTime();
                // serviceClass +$Client
                Class<T> clientClass = (Class<T>) Class.forName(serviceClass.getName() + "$Client");
                result = ThriftInvoker.invokes(clientClass, methodName, serviceUrl, parameters);
                // 成功调用
                return result;
            } catch (Exception e) {
                // 异常返回
                if (e instanceof NoSuchMethodException || e instanceof SecurityException) {
                    // 将异常抛出去
                    throw new RuntimeException("反射调用的方法不存在！或参数不匹配！[" + methodName + "]是否存在于契约" + serviceClass.getName() + "中");
                } else if (e instanceof TTransportException || e instanceof ConnectException
                        || e instanceof SocketException) {
                    // 加入异常列表
                    if (serviceRouteInfo != null) {
                        ExceptionCheck.updateCheckList(serviceRouteInfo, 0);
                    }
                    // 将异常抛出去
                    throw new RuntimeException("连接被拒绝，请检查地址为" + serviceRouteInfo.getServiceIP() + ":" + serviceRouteInfo.getServicePort() + "的" + serviceRouteInfo.getContractName() + "接口是否通信是否正常");
                } else if (e instanceof InvocationTargetException) {
                    InvocationTargetException invo = (InvocationTargetException) e;
                    Throwable targetException = invo.getTargetException();
                    System.out.println(invo.getCause().getMessage());
                    if (targetException instanceof TProtocolException) {
                        throw new RuntimeException("调用方法时异常，请检查thrift接口中定义的必要参数！"
                                + targetException.getMessage());
                    } 
                    ExceptionCheck.updateCheckList(serviceRouteInfo, 0);
//                    else if (targetException instanceof TTransportException) {
                    // throw new RuntimeException("反射调用方法时通信异常，请检查端口是否被占用！"
//                                + targetException.getMessage());
//                    }
                }
                // 将异常抛出去
                throw e;
            } finally {
                // 记录日志
                String returnValue = "";
                if (result == null) {
                    returnValue = "NULL";
                } else {
                    try {
                        if (result instanceof String) {
                            returnValue = String.valueOf(result);
                        } else {
                            returnValue = JsonUtil.toJson(result);
                        }
                    } catch (Exception ex) {
                        returnValue = "返回结果序列化失败！！！！";
                    }
                }
                // 记录日志
                LogOperate.writeClientAccLog(addtion, methodName, serviceUrl, returnValue, invokeMilles, parameters);
            }
        } catch (Exception e) {
            // 打印异常
            e.printStackTrace();
            MLogException mLogException = new MLogException(ExceptionLevel.Error, "v4150", StringUtil.getExceptionDetailStr(e));
            // 设置路由路径
            if (!StringUtil.isNullOrEmpty(serviceUrl)) {
                mLogException.setUrl("调用服务端的地址:" + serviceUrl);
            }
            // 普通异常
            LogOperate.writeExceptionLogSafe(mLogException);
            // 将异常抛出去
            throw new RuntimeException(e);
        }
    }

    /**
     * @服务路由调用，带参数类型
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            契约class
     * @param methodName
     *            调用的方法名
     * @param wsdlUrl
     *            服务路由的wsdl路径
     * @param parameters
     *            入参参数
     * @return 调用结果
     */
    public static <T, E> E invokesForTest(Class<T> serviceClass, String methodName, String wsdlUrl,
            Object[] parameters) {
        if (parameters == null) {
            parameters = new Object[0];
        }
        // 入参校验
        if (parameters.length > 5) {
            throw new InvalidParameterException("服务调用入参不能超过5个");
        }
        // 服务返回结果
        E result = null;
        try {
            // 调用前的处理
            InvokeProcess.beforeInvokeProcess(null, methodName);
            result = ThriftInvoker.invokes(serviceClass, methodName, wsdlUrl, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
