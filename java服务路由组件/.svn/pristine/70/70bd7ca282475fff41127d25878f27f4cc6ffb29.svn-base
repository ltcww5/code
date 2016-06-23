/*
 * 文件名：JaxWsProxyFactoryBeanPool.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JaxWsProxyFactoryBeanPool.java
 * 修改人：chunfeng
 * 修改时间：2015年4月21日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInvoke;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.better517na.JavaServiceRouteHelper.business.cxfInterceptor.ClientOutInterceptor;

/**
 * . JaxWsProxyFactoryBean 缓存池
 * 
 * @author chunfeng
 */
public final class JaxWsProxyFactoryBeanPool {
    /**
     * . 线程安全的池子
     */
    private ConcurrentLinkedQueue<JaxWsProxyFactoryBean> factoryPool = new ConcurrentLinkedQueue<JaxWsProxyFactoryBean>();

    /**
     * . 单例的唯一对象
     */
    private static JaxWsProxyFactoryBeanPool instance;

    /**
     * .
     * 
     * 构造函数.
     *
     */
    private JaxWsProxyFactoryBeanPool() {
    }

    /**
     * . 单例返回
     * 
     * @return 返回xmlbuilder线程池对象
     */
    public static JaxWsProxyFactoryBeanPool getInstance() {
        if (instance == null) {
            synchronized (JaxWsProxyFactoryBeanPool.class) {
                if (instance == null) {
                    instance = new JaxWsProxyFactoryBeanPool();
                }
            }
        }
        return instance;
    }

    /**
     * . 从线程池里面获取cxf工厂
     * 
     * @return 返回一个cxf工厂
     */
    public JaxWsProxyFactoryBean getCxfFactory() {
        JaxWsProxyFactoryBean factoryBean = factoryPool.poll();
        if (factoryBean == null) {
            // 工厂化
            factoryBean = new JaxWsProxyFactoryBean();
            // 添加一个拦截器
            factoryBean.getOutInterceptors().add(new ClientOutInterceptor());
            return factoryBean;
        }
        return factoryBean;
    }

    /**
     * . 退还
     * 
     * @param factoryBean
     *            退还的工厂
     */
    public void returnCxfFactory(JaxWsProxyFactoryBean factoryBean) {
        factoryPool.add(factoryBean);
    }
}
