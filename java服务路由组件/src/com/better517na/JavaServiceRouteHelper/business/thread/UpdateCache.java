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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.Cache.ServiceRouteCacheManager;
import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;

/**
 * . TODO 用于异步更新缓存的方法
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author chunfeng
 */
@Deprecated
public class UpdateCache implements Runnable {
    
    /**.
     * map结构 存储正在进行缓存更新的所有的契约
     */
    public static final ConcurrentMap<String, Date> WORKINGMAP = new ConcurrentHashMap<String, Date>();
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void run() {
        String mapKey = RouteCache.generateMapKey();
        Date cacheTime = WORKINGMAP.get(mapKey);
        
        //看当前的契约是否正在进行缓存更新
        if (cacheTime == null) {
            //记录正在工作
            WORKINGMAP.put(mapKey, new Date());
        } else {
            //判断是否已经过期  20秒还没返回就当他死掉了
            if (DateUtil.judgeTimeSecondWithNow(cacheTime, 20)) {
                //更新记录
                WORKINGMAP.put(mapKey, new Date());
            } else {
                //有线程正在更新缓存 直接返回
                return;
            }
        }
        
        //执行更新操作
        excute();
        
        //工作完成  从记录中删除
        WORKINGMAP.remove(mapKey);
    }
    
    /**.
     * 线程执行的业务方法
     */
    private void excute(){
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
    }
}
