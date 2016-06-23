/*
 * 文件名：CxfInvoke.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CxfInvoke.java
 * 修改人：chunfeng
 * 修改时间：2015年4月13日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInvoke;

import java.lang.reflect.Method;
import java.security.InvalidParameterException;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.cxfInterceptor.ClientOutInterceptor;
import com.better517na.JavaServiceRouteHelper.business.cxfInterceptor.ClientSetPostSubmitInterceptor;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * @实现cxf的远程调用实现 <p>
 *               详细描述
 *               <p>
 *               示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author chunfeng
 */
public class CxfInvoker {

    /**
     * TODO 路由服务主方法.
     * 
     * @param <T>
     *            泛型接口类
     * @param <E>
     *            返回值类型
     * @param serviceClass
     *            服务接口类
     * @param methodName
     *            方法名
     * @param serviceUrl
     *            调用的服务
     * @param param
     *            业务参数
     * @return 结果
     * @throws Exception
     *             Exception
     */
    @SuppressWarnings("unchecked")
    public static <T, E> E invokes(Class<T> serviceClass, String methodName, String serviceUrl, Object[] param) throws Exception {
        // 入参校验
        if (param == null) {
            param = new Object[0];
        }

        if (param.length > 5) {
            throw new InvalidParameterException("服务调用入参不能超过5个");
        }

        if (StringUtil.isNullOrEmpty(serviceUrl)) {
            throw new InvalidParameterException("服务地址serviceUrl不能为空");
        }

        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.getOutInterceptors().add(new ClientOutInterceptor());
        // 设置post方式
        factoryBean.getOutInterceptors().add(new ClientSetPostSubmitInterceptor());

        // 返回结果
        Object result = null;
        factoryBean.setServiceClass(serviceClass);
        factoryBean.setAddress(serviceUrl);

        T create = (T) factoryBean.create();

        // 设置超时时间
        setCxfTimeout(create);

        Method method = null;
        // 获取method
        method = HandleBizParam.getMethod(serviceClass, methodName, param);
        // 执行方法
        result = HandleBizParam.methodInvoke(create, method, param);

        if (result == null) {
            return null;
        }
        return (E) result;
    }

    /**
     * . 设置超时时间
     * 
     * @param <T>
     *            类型
     * @param create
     *            对象
     */
    private static <T> void setCxfTimeout(T create) {
        // 设置默认值
        long connectionSet = 20 * 1000L;
        long readSet = 60 * 1000L;
        try {
            connectionSet = Long.valueOf(RouteCache.cxfConnectionTimeout);
            readSet = Long.valueOf(RouteCache.cxfReadTimeout);
        } catch (Exception e) {
            System.out.println();
        }

        Conduit conduit = ClientProxy.getClient(create).getConduit();
        HTTPConduit hc = (HTTPConduit) conduit;
        HTTPClientPolicy client = new HTTPClientPolicy();
        client.setConnectionTimeout(connectionSet);
        client.setReceiveTimeout(readSet);
        hc.setClient(client);

    }
}
