package com.better517na.JavaServiceRouteHelper.business.thread;

/*
 * 文件名：UpdateCache.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UpdateCache.java
 * 修改人：chunfeng
 * 修改时间：2015年4月10日
 * 修改内容：新增
 */

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.logcompontent.model.MLogAcc;

/**
 * . TODO 写入客户端的acc日志
 * 
 * @author chunfeng
 */
public class WriteClientAccLog implements Runnable {
    
    /**.
     * 要进行日志记录的日志对象
     */
    private MLogAcc acc;
    
    /**.
     * 调用的服务的ip
     */
    private String serverIp;
    
    /**.
     * 
     * 构造函数.
     * 
     * @param acc acc日志对象
     * @param serverIp 调用服务的ip
     */
    public WriteClientAccLog(MLogAcc acc, String serverIp){
        this.acc = acc;
        this.serverIp = serverIp;
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
        if (acc == null) {
            acc = new MLogAcc();
        }
        LogOperate.writeClientAccLog(this.acc, this.serverIp);
    }
}
