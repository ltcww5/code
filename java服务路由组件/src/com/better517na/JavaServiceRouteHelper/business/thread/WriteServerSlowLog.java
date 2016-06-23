package com.better517na.JavaServiceRouteHelper.business.thread;

/*
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
import com.better517na.logcompontent.model.MLogSlowCall;

/**
 * . TODO 用于异步添加慢日志
 * 
 * @author chunfeng
 */
public class WriteServerSlowLog implements Runnable {
    
    /**.
     * 不用记日志的方法
     */
    public static final String[] EXCEPTMETHOD = {"heartBeatJava"};
    
    /**.
     * 输入流的内容
     */
    private String requestContent;
    
    /**.
     * 调用服务的client的ip
     */
    private String clientIp;
    
    /**.
     * 日志对象
     */
    private AsyTrackIDModel asymodel;
    
    /**.
     * 
     * 构造函数.
     * @param requestContent requestContent
     * @param clientIp clientIp
     * @param asymodel asymodel
     */
    public WriteServerSlowLog(String requestContent, String clientIp, AsyTrackIDModel asymodel) {
        this.asymodel = asymodel;
        this.requestContent = requestContent;
        this.clientIp = clientIp;
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
        MLogSlowCall slowLog =  new MLogSlowCall();
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
        slowLog.setMethod(requestMap.get(XmlStringAnalyze.MAPKEY_METHODNAME));
        slowLog.setParams("["+requestMap.get(XmlStringAnalyze.MAPKEY_PARAMETERS)+"]");
        slowLog.setServiceDesc("客户端是:"+clientIp);
        
        //处理一下调用时间
        LogOperate.writeServerSlowLog(slowLog, asymodel);
    }
}
