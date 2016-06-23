/*
 * 文件名：WebDestroyListener.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： WebDestroyListener.java
 * 修改人：chunfeng
 * 修改时间：2015年4月29日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteHelper;

/**
 * . 用于tomcate中 当服务路由卸载的时候使用
 * 
 * @author chunfeng
 */
public class JavaServiceRouteDestroyListener implements ServletContextListener {
    /**
     * {@inheritDoc}.
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // 关闭服务路由的资源
        ServiceRouteHelper.closeJavaService();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
    }
}
