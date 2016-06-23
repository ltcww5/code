/*
 * 文件名：ThreadOperateAbastract.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThreadOperateAbastract.java
 * 修改人：chunfeng
 * 修改时间：2015年5月7日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.thread;

import com.better517na.JavaServiceRouteHelper.business.Cache.RouteCache;
import com.better517na.JavaServiceRouteHelper.business.Cache.ThreadListCache;


/**.
 * 线程操作的父类
 * 
 * @author     chunfeng
 */
public abstract class AbstractThreadOperateImpl implements ThreadOperate, Runnable{

    /**.
     * 是否运行的标示符
     */
    private boolean alive = false;
    
    /**.
     * 构造函数.
     */
    public AbstractThreadOperateImpl(){
        //加入管理
        ThreadListCache.addToList(this);
        this.alive = true;
    }
    
    /** 
     * {@inheritDoc}.
     */
    @Override
    public void startRun() {
        this.alive = true;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public void stopRun() {
        this.alive = false;
        //退出线程管理
        ThreadListCache.removeFromList(this);
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public boolean isRun() {
        return this.alive;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public void run() {
        // 循环线程
        while (isRun()) {
            try {
                execute();
                Thread.sleep(getStopMilles());
            } catch (Exception e) {
                if (RouteCache.isDebug){
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**.
     * 
     * TODO 执行方法
     *
     */
    public abstract void execute();
    
    /**.
     * 没执行一次需要停止多长时间
     * @return 停顿时间长度 (毫秒)
     */
    public abstract long getStopMilles();

}
