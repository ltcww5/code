/*
 * 文件名：ThreadOperate.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThreadOperate.java
 * 修改人：chunfeng
 * 修改时间：2015年5月7日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.thread;

/**.
 * TODO 线程统一操作接口
 * @author     chunfeng
 */
public interface ThreadOperate {
    
    /**.
     * 
     * TODO 启动线程
     *
     */
    void startRun();
    
    /**.
     * 
     * TODO 停止线程
     *
     */
    void stopRun();
    
    /**.
     * 
     * TODO 判断线程是否在运行
     * @return 是否在运行
     */
    boolean isRun();
}
