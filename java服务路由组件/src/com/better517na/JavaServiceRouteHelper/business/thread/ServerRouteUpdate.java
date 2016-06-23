package com.better517na.JavaServiceRouteHelper.business.thread;

/*
 * 文件名：UpdateCache.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UpdateCache.java
 * 修改人：chunfeng
 * 修改时间：2015年4月10日
 * 修改内容：新增
 */

import java.util.Date;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.Cache.ServiceRouteCacheManager;
import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * . TODO 用于异步更新缓存的方法 服务路由列表
 */
public class ServerRouteUpdate extends AbstractThreadOperateImpl {
    /**
     * . 更新服务路由列表的间隔时间
     */
    private static final int PERIOD_MINITUES = 2;
    

    /** 
     * {@inheritDoc}.
     */
    @Override
    public void execute() {
        try {
            // 查询
            List<ServiceRouteInfoCache> routeCacheList = RouteCache.getServiceRouteInfoCache();
    
            // 有数据 则更新缓存
            if (routeCacheList != null && routeCacheList.size() > 0) {
                // 封装成一个model 用于缓存
                ServiceCacheModel<ServiceRouteInfoCache> value = new ServiceCacheModel<ServiceRouteInfoCache>();
                value.setCacheList(routeCacheList);
                value.setCacheTime(new Date());
                // 加入路由缓存列表
                ServiceRouteCacheManager.setServiceRouteCache(RouteCache.generateMapKey(), value);
            }
        } catch (Exception e){
            LogOperate.writeExceptionLogSafe(new MLogException(ExceptionLevel.Error, "v4150", 
                    "异步更新服务路由列表异常", e));
        } 
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public long getStopMilles() {
        return 1000 * 60 * PERIOD_MINITUES;
    }
}
