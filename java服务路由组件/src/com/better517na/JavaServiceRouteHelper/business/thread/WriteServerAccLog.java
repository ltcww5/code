package com.better517na.JavaServiceRouteHelper.business.thread;

/*
 * 文件名：UpdateCache.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UpdateCache.java
 * 修改人：chunfeng
 * 修改时间：2015年4月10日
 * 修改内容：新增
 */

import java.util.Map;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.business.servlet.xmlAnalyze.XmlStringAnalyze;
import com.better517na.JavaServiceRouteHelper.model.AsyTrackIDModel;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;
import com.better517na.logcompontent.model.MLogAcc;

/**
 * . TODO 用于异步添加acc日志
 * 
 * @author chunfeng
 */
public class WriteServerAccLog implements Runnable {
    
    /**.
     * 不用记日志的方法
     */
    public static final String[] EXCEPTMETHOD = {"heartBeatJava"};
    
    /**.
     * 输入流的内容
     */
    private String requestContent;
    
    /**.
     * 输出流的内容
     */
    private String responseContent;
    
    /**.
     * 调用服务的client的ip
     */
    private String clientIp;
    
    /**.
     * 日志对象
     */
    private AsyTrackIDModel asymodel;
    
    /**.
     * 调用开始时间
     */
    private long invokeStartTime;
    
    /**.
     * 
     * 构造函数.
     * @author chunfeng
     * @param requestContent requestContent
     * @param responseContent responseContent
     * @param clientIp clientIp
     * @param asymodel asymodel
     * @param invokeStartTime invokeStartTime
     */
    public WriteServerAccLog(String requestContent, String responseContent, String clientIp, AsyTrackIDModel asymodel, long invokeStartTime){
        this.asymodel = asymodel;
        this.requestContent = requestContent;
        this.responseContent = responseContent;
        this.clientIp = clientIp;
        this.invokeStartTime = invokeStartTime;
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void run() {
        //执行更新操作
        excute();
    }
    
    /**.
     * 线程执行的业务方法
     */
    private void excute(){
        MLogAcc   acc = new MLogAcc();
        //解析入参和方法
        Map<String, String> requestMap = XmlStringAnalyze.analyzeRequestXml(requestContent, false);
        
        String methdoName = requestMap.get(XmlStringAnalyze.MAPKEY_METHODNAME);
        
        //去除不需要记日志的方法
        for (int i = 0; i<EXCEPTMETHOD.length; i++) {
            if (EXCEPTMETHOD[i].equals(methdoName)) {
                return;
            } 
        }
        
        //method  parameters
        acc.setMethod(requestMap.get(XmlStringAnalyze.MAPKEY_METHODNAME));
        acc.setParas("["+requestMap.get(XmlStringAnalyze.MAPKEY_PARAMETERS)+"]");
        
        //解析返回结果
        Map<String, String> responseMap = XmlStringAnalyze.analyzeResponseXml(responseContent);
        //返回结果
        acc.setReturnValue(responseMap.get(XmlStringAnalyze.MAPKEY_RETURNVAL));
        
        //设置调用的时间长度
        acc.setTimePeriod(DateUtil.getNowWitTimeGap(invokeStartTime));
        LogOperate.writeServerAccLog(acc, this.clientIp, asymodel);
    }
}
