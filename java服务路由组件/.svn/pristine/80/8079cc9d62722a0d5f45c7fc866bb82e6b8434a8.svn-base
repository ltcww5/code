package com.better517na.JavaServiceRouteHelper.business.thread;

import java.util.Date;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.Cache.ServiceRouteCacheManager;
import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**.
 * 宕机线程.
 * 
 * @author yishao
 *
 */
public class DowntimeThread extends AbstractThreadOperateImpl {
    /**
     * . 默认的缓存有效时间
     */
    private static final int CACHE_MINITUES = 2;

    /** 
     * {@inheritDoc}.
     */
    @Override
    public void execute() {
        try {
            // 查询宕机表
            List<ServiceDowntimeInfo> serviceDowntimeInfoList = LoadSpring.getIServiceRouteServiceBean().getServiceDowntimeInfo();
            if (serviceDowntimeInfoList != null && serviceDowntimeInfoList.size() > 0) {
                ServiceCacheModel<ServiceDowntimeInfo> newCache = new ServiceCacheModel<ServiceDowntimeInfo>();
                newCache.setCacheList(serviceDowntimeInfoList);
                newCache.setCacheTime(new Date());
                // 加入宕机缓存
                ServiceRouteCacheManager.setServiceDowntimeInfoCache(newCache);
            }
        } catch (Exception e) {
            LogOperate.writeExceptionLogSafe(new MLogException(ExceptionLevel.Error, "v4150", 
                    "宕机服务列表更新线程异常", e));
        }
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public long getStopMilles() {
        return 1000 * 60 * CACHE_MINITUES;
    }
}
