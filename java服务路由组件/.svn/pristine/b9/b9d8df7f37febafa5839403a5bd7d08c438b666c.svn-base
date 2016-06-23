/*
 * 文件名：InvokeProcess.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： InvokeProcess.java
 * 修改人：chunfeng
 * 修改时间：2015年6月11日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInvoke;

import java.util.Date;

import com.better517na.JavaServiceRouteHelper.business.Cache.AccLogWriteConfig;
import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.model.AdditionalParam;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.logcompontent.model.MTrackID;

/**
 * . 关于服务调用的一些处理方法
 * 
 * @author chunfeng
 */
public class InvokeProcess {

    /**
     * . 在服务调用之前进行处理
     * 
     * @param addtion
     *            addtion
     * @param methodName 调用的方法名
     */
    public static void beforeInvokeProcess(AdditionalParam addtion, String methodName){
        MTrackID mTrackID = LogOperate.getTrackIdObj();

        // 在服务调用之前 记录accClient日志 设置key1 ,key2 ,key3
        if (addtion != null) {
            // 设置到线程变量中
            mTrackID.setKey1(addtion.getLogKey1());
            mTrackID.setKey2(addtion.getLogKey2());
            mTrackID.setKey3(addtion.getLogKey3());
            // 将addtion传到服务端
            ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.ADDTIONALPARAM_KEY, JsonUtil.toJson(addtion));
        }

        // 将trackId放入attach中
        ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.TRACKID_VAL_KEY, mTrackID.getTrackId());
        ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.TRACKID_STARTTIME_KEY, DateUtil.getStardDateStr(mTrackID.getStartTime()));

        // 记录调用的当前时间
        ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.SLOWLOGSTARTTIME, "" + new Date().getTime());
        
        //告诉远端是否记录acc日志(不写就加标识)
        if (AccLogWriteConfig.writeAccLog(methodName)){
            ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.REMOTEWRITEACCLOGKEY, "true");
        } else {
            ServiceRouteAttachHelper.getInstance().setAttachVal(LogOperate.REMOTEWRITEACCLOGKEY, "false");
        }
    }
}
