/*
 * 文件名：LogAttachModel.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogAttachModel.java
 * 修改人：chunfeng
 * 修改时间：2015年4月17日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.model;

import java.util.Date;

/**
 * . TODO 传输acc调用的附加信息
 * @author chunfeng
 */
public class AdditionalParam {
    /**
     * . logKey1
     */
    private String logKey1;

    /**
     * . logKey2
     */
    private String logKey2;

    /**
     * . logKey3
     */
    private String logKey3;

    /**
     * . timeout
     */
    private Date timeout;
    
    /**
     * 设置logKey1.
     * 
     * @return 返回logKey1
     */
    public String getLogKey1() {
        return logKey1;
    }

    /**
     * 获取logKey1.
     * 
     * @param logKey1
     *            要设置的logKey1
     */
    public void setLogKey1(String logKey1) {
        this.logKey1 = logKey1;
    }

    /**
     * 设置logKey2.
     * 
     * @return 返回logKey2
     */
    public String getLogKey2() {
        return logKey2;
    }

    /**
     * 获取logKey2.
     * 
     * @param logKey2
     *            要设置的logKey2
     */
    public void setLogKey2(String logKey2) {
        this.logKey2 = logKey2;
    }

    /**
     * 设置logKey3.
     * 
     * @return 返回logKey3
     */
    public String getLogKey3() {
        return logKey3;
    }

    /**
     * 获取logKey3.
     * 
     * @param logKey3
     *            要设置的logKey3
     */
    public void setLogKey3(String logKey3) {
        this.logKey3 = logKey3;
    }

    /**
     * 设置timeout.
     * 
     * @return 返回timeout
     */
    public Date getTimeout() {
        return timeout;
    }

    /**
     * 获取timeout.
     * 
     * @param timeout
     *            要设置的timeout
     */
    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }
}
