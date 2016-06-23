/*
 * 文件名：LogOperate.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogOperate.java
 * 修改人：chunfeng
 * 修改时间：2015年4月17日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.thrift.log;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.log4j.Logger;

import com.better517na.JavaServiceRouteHelper.business.Cache.AccLogWriteConfig;
import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.business.thread.WriteLogThreadPool;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.model.AsyTrackIDModel;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.thrift.thread.WriteServerAccLog;
import com.better517na.JavaServiceRouteHelper.thrift.thread.WriteServerSlowLog;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.JavaServiceRouteHelper.util.SystemTool;
import com.better517na.logcompontent.model.MLogAcc;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.model.MLogSlowCall;
import com.better517na.logcompontent.model.MTrackID;
import com.better517na.logcompontent.util.ThreadID;
import com.better517na.logcompontent.util.TrackIdManager;

/**
 * . 日志操作的类
 * 
 * @author chunfeng
 */
public class ThriftLogOperate {
    /**.
     * 日志
     */
    private static Logger log = Logger.getLogger(ThriftLogOperate.class);
    
    /**
     * . addtionalParam的标识
     */
    public static final String ADDTIONALPARAM_KEY = "logAddtionalParam";

    /**
     * . TrackId
     */
    public static final String TRACKID_KEY = "TrackId";

    /**
     * . trackID
     */
    public static final String TRACKID_VAL_KEY = "trackID";

    /**
     * . startTime
     */
    public static final String TRACKID_STARTTIME_KEY = "startTime";

    /**
     * . http://log.517na.com
     */
    public static final String NAMESPACEURI = "TrackId.517na.com";

    /**
     * . 记录满日志的标示符
     */
    public static final String SLOWLOGSTARTTIME = "517slowlogtime";
    
    /**.
     * ***********************************记录acc日志相关******************************
     */
    /**.
     * 记录acc日志的标识 true or false
     */
    public static final String REMOTEWRITEACCLOGKEY = "remokeWriteAccLogKey";

    /**
     * . trackIdManager对象
     */
    public static TrackIdManager trackIdManager;

    // 生成trackIdManager对象
    static {
        trackIdManager = new TrackIdManager();
    }

    /**
     * . 写客户端日志 同步方法
     * 
     * @param addtion
     *            addtion
     * @param methodName
     *            调用的方法名
     * @param serverIp
     *            serverIp
     * @param resultValue
     *            返回值
     * @param invokeMilles
     *            调用的时间
     * @param param
     *            param
     */
    public static void writeClientAccLog(AdditionalParam addtion, String methodName, String serverIp, String resultValue, long invokeMilles, Object[] param) {
        //加个日志记录开关
        if (!AccLogWriteConfig.writeAccLog(methodName)){
            return;
        }
        MTrackID mTrackID = getTrackIdObj();
        MLogAcc clientAcc = new MLogAcc();

        // 在服务调用之前 记录accClient日志 设置key1 ,key2 ,key3
        if (addtion != null) {
            // 设置到线程变量中
            mTrackID.setKey1(addtion.getLogKey1());
            mTrackID.setKey2(addtion.getLogKey2());
            mTrackID.setKey3(addtion.getLogKey3());

            // 设置到对象中
            clientAcc.setKey1(addtion.getLogKey1());
            clientAcc.setKey2(addtion.getLogKey2());
            clientAcc.setKey3(addtion.getLogKey3());
        }

        clientAcc.setMethod(methodName);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < param.length; i++) {
            sb.append(getParameterStr(param[i]));
            if (i != (param.length - 1)) {
                sb.append(",");
            }
        }
        sb.append("]");
        clientAcc.setParas(sb.toString());
        clientAcc.setReturnValue(resultValue);
        // 调用时间
        clientAcc.setTimeTicks(invokeMilles);
        clientAcc.setTimePoint(new Timestamp(invokeMilles));
        
        //计算调用的时间间隔
        clientAcc.setTimePeriod(DateUtil.getNowWitTimeGap(invokeMilles));
        // 调用后续记录日志的方法
        writeClientAccLog(clientAcc, serverIp);
    }

    /**
     * . 将入参转化未string类型
     * 
     * @param param
     *            入参参数
     * @return 字符串参数
     */
    private static String getParameterStr(Object param) {
        if (param == null) {
            return "";
        }
        try {
            if (param instanceof String) {
                return String.valueOf(param);
            } else {
                return JsonUtil.toJson(param);
            }
        } catch (Exception ex) {
            return "[" + param + "]转化字符串失败";
        }
    }

    /**
     * . 客户端添加acc日志 异步写入日志
     * 
     * @param acc
     *            acc日志对象
     * @param serverIp
     *            准备调用的服务端的ip
     */
    public static void writeClientAccLog(MLogAcc acc, String serverIp) {
        acc.setDirection(LogDirection.REPLY);

        acc.setLocalIP(SystemTool.localIp);
        acc.setMachineName(RouteCache.uniqueSign);
        // 服务地址
        acc.setServiceAddress(serverIp);
        acc.setDescription("程序唯一标识:"+RouteCache.uniqueSign);
        
        ThriftLogOperate.writeAccLogSafe(acc);
    }

    /**
     * @写日志
     * @param trackMap trackMap
     * @param returnVal returnVal
     */
    public static void writeServerAccLog(Map<String, Object> trackMap, Object returnVal) {

        String clientIP = (String) trackMap.get("Thrift.clientIP");
        if (!ThreadLocalLogAttach.wirteAccLog()){
            return;
        }
        // 传输model
        AsyTrackIDModel asymodel = new AsyTrackIDModel();

        // 解析trackId
        MTrackID mTrackID = ThriftLogOperate.getTrackIdObj();

        // 设置trackId相关数据
        String clientAddtionStr = (String) trackMap.get(ThriftLogOperate.ADDTIONALPARAM_KEY);

        // 默认值
        AdditionalParam clientAddtion = null;
        if (clientAddtionStr != null && !"".equals(clientAddtionStr)) {
            clientAddtion = JsonUtil.toObject(clientAddtionStr, AdditionalParam.class);
        }
        // addtion
        asymodel.setAddtion(clientAddtion);
        asymodel.setTrackId(mTrackID.getTrackId());
        asymodel.setTrackIdStartTime(System.currentTimeMillis());
        //        DateUtil.getNowWitTimeGap(mTrackID.getStartTime())
        // 最后耗时的 交给异步去处理
        WriteLogThreadPool.getBTaskThreadPool().loadTasks(new WriteServerAccLog(trackMap, returnVal, clientIP, asymodel, ThreadLocalLogAttach.getTimeStamp()));
    }

    /**
     * . 服务器端添加acc日志 异步方法
     * 
     * @param acc
     *            acc日志对象
     * @param clientIp
     *            调用该服务的客户端ip
     * @param asymodel
     *            trackId相关
     */
    public static void writeServerAccLog(MLogAcc acc, String clientIp, AsyTrackIDModel asymodel) {
        // 设置trackId相关数据
        MTrackID mTrackID = ThriftLogOperate.getTrackIdObj();
        mTrackID.setTrackId(asymodel.getTrackId());
        mTrackID.setStartTime(asymodel.getTrackIdStartTime());

        if (asymodel.getAddtion() != null) {
            mTrackID.setKey1(asymodel.getAddtion().getLogKey1());
            mTrackID.setKey2(asymodel.getAddtion().getLogKey2());
            mTrackID.setKey3(asymodel.getAddtion().getLogKey3());

            // 设置acc
            acc.setKey1(asymodel.getAddtion().getLogKey1());
            acc.setKey2(asymodel.getAddtion().getLogKey2());
            acc.setKey3(asymodel.getAddtion().getLogKey3());
        }

        acc.setDirection(LogDirection.RESPONSE);

        // 时间戳
        long currentMills = System.currentTimeMillis();
        acc.setTimeTicks(currentMills);
        acc.setTimePoint(new Timestamp(currentMills));

        acc.setLocalIP(SystemTool.localIp);
        acc.setMachineName(RouteCache.uniqueSign);

        // 远端地址
        acc.setServiceAddress(clientIp);
        acc.setDescription("");

        //计算调用的时间间隔

        acc.setTimePeriod(DateUtil.getNowWitTimeGap(asymodel.getTrackIdStartTime()));
        ThriftLogOperate.writeAccLogSafe(acc);
    }

    /**
     * . 记录慢日志
     * 
     * @param slowLog
     *            慢日志对象
     * @param asymodel
     *            trackId相关
     */
    public static void writeServerSlowLog(MLogSlowCall slowLog, AsyTrackIDModel asymodel) {
        // 设置trackId相关数据
        MTrackID mTrackID = ThriftLogOperate.getTrackIdObj();
        mTrackID.setTrackId(asymodel.getTrackId());
        mTrackID.setStartTime(asymodel.getTrackIdStartTime());

        slowLog.setLocalIP(SystemTool.localIp);
        slowLog.setMachineName(RouteCache.uniqueSign);
        
        ThriftLogOperate.writeSlowLogSafe(slowLog);
    }

    /**
     * . 记录慢日志
     * 
     * @param requestStr
     *            requestStr
     * @param invokeTime
     *            调用的时间
     * @param clientIp
     *            clientIp
     */
    public static void writeServerSlowLog(Map<String, Object> requestStr, String invokeTime, String clientIp) {
        // 传输model
        AsyTrackIDModel asymodel = new AsyTrackIDModel();
        // 解析trackId
        MTrackID mTrackID = ThriftLogOperate.getTrackIdObj();
        // 设置调用时间
        // 设置trackId相关数据
        String clientAddtionStr = ServiceRouteAttachHelper.getInstance().getAttachVal(ThriftLogOperate.ADDTIONALPARAM_KEY);

        // 默认值
        AdditionalParam clientAddtion = null;
        if (clientAddtionStr != null && !"".equals(clientAddtionStr)) {
            clientAddtion = JsonUtil.toObject(clientAddtionStr, AdditionalParam.class);
        }
        // addtion
        asymodel.setAddtion(clientAddtion);
        asymodel.setTrackId(mTrackID.getTrackId());
        long linvokeTime = Long.valueOf(invokeTime);
        asymodel.setTrackIdStartTime(linvokeTime);

        // 最后耗时的 交给异步去处理
        WriteLogThreadPool.getBTaskThreadPool().loadTasks(new WriteServerSlowLog(requestStr, clientIp, asymodel));
    }

    /**
     * . 获取当前线程中的trackId相关的对象
     * 
     * @return MTrackID
     */
    public static MTrackID getTrackIdObj() {
        // 获取trackId
        ThreadID threadID = new ThreadID();
        MTrackID mTrackID = (MTrackID) threadID.get();
        if (mTrackID == null) {
            mTrackID = trackIdManager.createTrackId("jseroute");
            threadID.set(mTrackID);
        }
        return mTrackID;
    }
    
    /**.
     * 安全的输入accLog的方法
     * @param accLog acclog
     */
    public static void writeAccLogSafe(MLogAcc accLog){
        if (accLog == null){
            return;
        }
        try {
            LoadSpring.getLogBusiness().writeAccLog(accLog);
        } catch (Exception ex){
            log.warn("写入acclog异常！！！");
        }
    }
    
    /**.
     * 安全的输入slowlog的方法
     * 
     * @param slowLog slowLog
     */
    public static void writeSlowLogSafe(MLogSlowCall slowLog){
        if (slowLog == null){
            return;
        }
        try {
            LoadSpring.getLogBusiness().writeSlowCallLog(slowLog);
        } catch (Exception ex){
            log.warn("写入slowLog异常！！！");
        }
    }
    
    /**.
     * 安全的输入异常日志的方法
     * @param exceptionLog 异常日志
     */
    public static void writeExceptionLogSafe(MLogException exceptionLog){
        if (exceptionLog == null){
            return;
        }
        try {
            LoadSpring.getLogBusiness().writeExceptionLog(exceptionLog);
        } catch (Exception ex){
            log.warn("写入exceptionLog异常！！！");
        }
    }
}
