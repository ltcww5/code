/*
 * 文件名：LogAttach.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogAttach.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.operateData.impl;

import java.util.Date;
import java.util.Map;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.business.log.ThreadLocalLogAttach;
import com.better517na.JavaServiceRouteHelper.business.servlet.operateData.OperateHttpData;
import com.better517na.JavaServiceRouteHelper.business.servlet.xmlAnalyze.XmlStringAnalyze;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.model.CxfTransferModel;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.model.MTrackID;
import com.better517na.logcompontent.util.ExceptionLevel;

/**
 * . TODO 添加日志和attach处理
 * 
 * @author chunfeng
 */
public class LogAttach implements OperateHttpData {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void beforeInvoke(String content, String clientIp) {
        // 查看是否需要处理
        if (!XmlStringAnalyze.judgeCanAnalyze(content)) {
            return;
        }
        // 解析入参和方法
        Map<String, String> requestMap = XmlStringAnalyze.analyzeRequestXml(content, true);

        try {
            // 在线程开始的地方设置trackId
            MTrackID mTrackID = LogOperate.getTrackIdObj();

            // 将trackId获取到
            String trackId = requestMap.get(LogOperate.TRACKID_VAL_KEY);
            String startTime = requestMap.get(LogOperate.TRACKID_STARTTIME_KEY);

            // 添加trackId和startTime
            mTrackID.setTrackId(trackId);
            mTrackID.setStartTime(DateUtil.getStardDateLong(startTime));

            // 获取attach
            String attach = requestMap.get(XmlStringAnalyze.MAPKEY_ATTACH);
            // 判空处理
            if (attach == null || "".equals(attach)) {
                // 覆盖空值
                ServiceRouteAttachHelper.THREAD_ATTACH_OUT.set(null);
                return;
            }

            CxfTransferModel cxf = JsonUtil.toObject(attach, CxfTransferModel.class);
            // 不管有没有值 都覆盖
            ServiceRouteAttachHelper.THREAD_ATTACH_OUT.set(cxf.getAttach());
            // 设置trackId相关数据
            String clientAddtionStr = ServiceRouteAttachHelper.getInstance().getAttachVal(LogOperate.ADDTIONALPARAM_KEY);

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
            ThreadLocalLogAttach.setWriteAccLog(!"false".equals(ServiceRouteAttachHelper.getInstance().getAttachVal(LogOperate.REMOTEWRITEACCLOGKEY)));
            
            // 记录慢日志
            String invokeStartTime = ServiceRouteAttachHelper.getInstance().getAttachVal(LogOperate.SLOWLOGSTARTTIME);
            if (!StringUtil.isNullOrEmpty(invokeStartTime)) {
                // 记录慢日志
                long dvalue = getInvokeTimeDValue(invokeStartTime);
                // 1.5秒 记录慢日志
                if (dvalue >= 1500) {
                    LogOperate.writeServerSlowLog(content, invokeStartTime, clientIp);
                }
            }
        } catch (Exception ex) {
            LogOperate.writeExceptionLogSafe(new MLogException(ExceptionLevel.Error, "v4150", "设置trackId出错", ex));
        } finally {
            //设置辅助时间点
            ThreadLocalLogAttach.setNewTimeStamp(new Date().getTime());
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void afterInvoke(String requestStr, String responseStr, String clientIp) {
        if (!XmlStringAnalyze.judgeCanAnalyze(requestStr)) {
            return;
        }
        // 写服务端日志
        LogOperate.writeServerAccLog(requestStr, responseStr, clientIp);
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
