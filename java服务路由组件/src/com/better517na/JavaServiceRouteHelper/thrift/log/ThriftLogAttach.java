/*
 * 文件名：LogAttach.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogAttach.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.thrift.log;

import java.util.Date;
import java.util.Map;

import com.better517na.JavaServiceRouteHelper.business.log.ThreadLocalLogAttach;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.model.MTrackID;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * @添加日志和attach处理
 * 
 * @author guangyin
 */
public class ThriftLogAttach {
    /**
     * @声明静态的单例对象的变量.
     */
    private static volatile ThriftLogAttach logAttach;

    /**
     * @外部通过此方法可以获取对象
     * 
     * @return 实例
     */
    public static ThriftLogAttach getInstance() {
        if (logAttach == null) {
            synchronized (ThriftLogAttach.class) {
                if (logAttach == null) {
                    logAttach = new ThriftLogAttach();
                }
            }
        }
        return logAttach;
    }

    /**
     * @执行方法前
     * @param logMap 请求内容
     * @param clientIp 客户端ip
     */
    public void beforeInvoke(Map<String, Object> logMap, String clientIp) {
        try {
            // 在线程开始的地方设置trackId
            MTrackID mTrackID = ThriftLogOperate.getTrackIdObj();

            // 将trackId获取到
            String trackId = (String) logMap.get(ThriftLogOperate.TRACKID_VAL_KEY);
            String startTime = (String) logMap.get(ThriftLogOperate.TRACKID_STARTTIME_KEY);

            // 添加trackId和startTime
            mTrackID.setTrackId(trackId);
            mTrackID.setStartTime(DateUtil.getStardDateLong(startTime));
            // 设置trackId相关数据
            String clientAddtionStr = (String) logMap.get(ThriftLogOperate.ADDTIONALPARAM_KEY);
            // 获取attach

            // 默认值
            AdditionalParam clientAddtion = null;
            if (clientAddtionStr != null && !"".equals(clientAddtionStr)) {
                clientAddtion = JsonUtil.toObject(clientAddtionStr, AdditionalParam.class);
            }

            // addtional
            if (clientAddtion != null) {
                mTrackID.setKey1(clientAddtion.getLogKey1());
                mTrackID.setKey2(clientAddtion.getLogKey2());
                mTrackID.setKey3(clientAddtion.getLogKey3());
            }

            //设置是否记录acc日志的标识
            ThreadLocalLogAttach.setWriteAccLog(!"false".equals((String) logMap.get(ThriftLogOperate.REMOTEWRITEACCLOGKEY)));
            
            // 记录慢日志
            String invokeStartTime = (String) logMap.get(ThriftLogOperate.SLOWLOGSTARTTIME);
            if (!StringUtil.isNullOrEmpty(invokeStartTime)) {
                // 记录慢日志
                long dvalue = getInvokeTimeDValue(invokeStartTime);
                // 1.5秒 记录慢日志
                if (dvalue >= 1500) {
                    ThriftLogOperate.writeServerSlowLog(logMap, invokeStartTime, clientIp);
                }
            }
        } catch (Exception ex) {
            ThriftLogOperate.writeExceptionLogSafe(new MLogException(ExceptionLevel.Error, "v6780", "设置trackId出错", ex));
        } finally {
            //设置辅助时间点
            ThreadLocalLogAttach.setNewTimeStamp(new Date().getTime());
        }
    }

    /**
     * .
     * 
     * 计算当前时间与调用时间的差值(毫秒)
     * 
     * @param invokeTimeStr
     *            invokeTimeStr
     * @return 差值
     */
    private long getInvokeTimeDValue(String invokeTimeStr) {
        long defualtDvalue = -1;
        try {
            defualtDvalue = Long.valueOf(invokeTimeStr);
        } catch (Exception ex) {
            return -1;
        }
        long nowl = new Date().getTime();
        return nowl - defualtDvalue;
    }
}
