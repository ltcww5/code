package com.better517na.JavaServiceRouteHelper.business.serviceRouteHelper;

import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import javax.xml.ws.WebServiceException;

import org.apache.cxf.interceptor.Fault;
import org.apache.log4j.Logger;

import com.better517na.JavaServiceRouteHelper.business.Cache.ExceptionCheck;
import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.cxfInvoke.CxfInvoker;
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
 * 路由服务入口.
 * 
 * @author yishao
 *
 */
public final class ServiceRouteHelperCore {
    /**.
     * 日志
     */
    private static Logger log = Logger.getLogger(ServiceRouteHelperCore.class);
    
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
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, Class<E> cla, AdditionalParam addtion, Object[] parameters, Class<?>[] parameterTypes) {
        if (parameters == null) {
            parameters = new Object[0];
        }

        // 入参校验
        if (parameters.length > 5) {
            throw new InvalidParameterException("服务调用入参不能超过5个");
        }

        // 传入了类型参数 则需要验证
        if (parameterTypes != null) {
            if (parameters.length != parameterTypes.length) {
                throw new InvalidParameterException("入参参数与入参类型数量不匹配");
            }
        }
        // 轮询 结果
        ServiceRouteInfoCache serviceRouteInfo = null;
        //调用的路由
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
                throw new RuntimeException("服务路由组件异常，" + sb.toString());
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
                result = CxfInvoker.invokes(serviceClass, methodName, serviceUrl, parameters);
                // 成功调用
                return result;
            } catch (Exception e) {
                // 异常返回
                if (e instanceof NoSuchMethodException || e instanceof SecurityException) {
                    log.warn("反射调用的方法不存在！！！！["+methodName+"]是否存在于契约"+serviceClass.getName()+"中");
                    // 将异常抛出去
                    throw e;
                }
                if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof InvocationTargetException) {
                    // 需要加入异常列表的exception
                    if (e instanceof InvocationTargetException) {
                        // 反射调用出问题
                        InvocationTargetException invo = (InvocationTargetException) e;
                        Throwable targetException = invo.getTargetException();
                        // 普通异常
                        if (targetException instanceof Fault) {
                            // cxf框架调用暴露的问题
                            Fault webException = (Fault) targetException;
                            Throwable causeException = webException.getCause();
                            // 暴露问题的原因是连接原因和socket的timeout
                            if (causeException instanceof ConnectException || causeException instanceof SocketTimeoutException || causeException instanceof SocketException) {
                                // 加入异常列表
                                if (serviceRouteInfo != null) {
                                    ExceptionCheck.updateCheckList(serviceRouteInfo, 0);
                                }
                                // 将异常抛出去
                                throw e;
                            }
                        }
                        // 运行时异常
                        if (targetException instanceof WebServiceException) {
                            // cxf框架调用暴露的问题
                            WebServiceException webException = (WebServiceException) targetException;
                            Throwable causeException = webException.getCause();
                            // 暴露问题的原因是连接原因和socket的timeout
                            if (causeException instanceof ConnectException || causeException instanceof SocketTimeoutException || causeException instanceof SocketException) {
                                // 加入异常列表
                                if (serviceRouteInfo != null) {
                                    ExceptionCheck.updateCheckList(serviceRouteInfo, 0);
                                }
                                // 将异常抛出去
                                throw e;
                            }
                        }
                    }
                }
                // 将异常抛出去
                throw e;
            } finally {
                //记录日志
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
                //记录日志
                LogOperate.writeClientAccLog(addtion, methodName, serviceUrl, returnValue, invokeMilles, parameters);
            }
        } catch (Exception e) {
            // 打印异常
            if (RouteCache.isDebug) {
                e.printStackTrace();
            }
            
            MLogException mLogException = new MLogException(ExceptionLevel.Error, "v4150", StringUtil.getExceptionDetailStr(e));
            // 设置路由路径
            if (!StringUtil.isNullOrEmpty(serviceUrl)){
                mLogException.setUrl("调用服务端的地址:"+serviceUrl);
            }
            // 普通异常
            LogOperate.writeExceptionLogSafe(mLogException);
            // 将异常抛出去
            throw new RuntimeException(e);
        }
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
     * @param wsdlUrl
     *            服务路由的wsdl路径
     * @param parameters
     *            入参参数
     * @return 调用结果
     */
    public static <T, E> E invokesForTest(Class<T> serviceClass, String methodName, String wsdlUrl, Object[] parameters) {
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
            result = CxfInvoker.invokes(serviceClass, methodName, wsdlUrl, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
