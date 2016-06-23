package com.better517na.JavaServiceRouteHelper.business.thread;

import com.better517na.JavaServiceRouteHelper.business.Cache.ExceptionCheck;
import com.better517na.JavaServiceRouteHelper.business.heartbeat.HeartBeatHelper;
import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * 心跳检测线程.
 * 
 * @author yishao
 *
 */
public class HeartBeatThread extends AbstractThreadOperateImpl  {
    /**
     * . 更新缓存的时间间隔
     */
    private static final int CACHE_SECONDS = 30;

    /** 
     * {@inheritDoc}.
     */
    @Override
    public void execute() {
        try {
            // 遍历异常列表
            if (ExceptionCheck.checkList.size() > 0) {
                for (ServiceRouteInfoCache serviceRouteInfoCache : ExceptionCheck.checkList.values()) {
                    HeartBeatHelper.hearBeat(serviceRouteInfoCache);
                }
            }
        } catch (Exception e) {
            LogOperate.writeExceptionLogSafe(new MLogException(ExceptionLevel.Error, "v4150", 
                    "异常服务列表检查线程异常[异常服务心跳检查]", e));
        }
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public long getStopMilles() {
        return 1000 * CACHE_SECONDS;
    }
}
