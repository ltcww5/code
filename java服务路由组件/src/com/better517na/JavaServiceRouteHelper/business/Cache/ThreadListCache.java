/*
 * 文件名：ThreadListCache.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThreadListCache.java
 * 修改人：chunfeng
 * 修改时间：2015年10月20日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.Cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.thread.AbstractThreadOperateImpl;

/**
 * 线程列表管理.
 * @author     chunfeng
 */
public class ThreadListCache {
    /**
     * . 用于统一处理线程
     */
    private static List<AbstractThreadOperateImpl> allThread = new ArrayList<AbstractThreadOperateImpl>();
    
    /**.
     * 添加线程到缓存中进行管理.
     * @param newThread newThread
     */
    public static void addToList(AbstractThreadOperateImpl newThread){
        if (newThread != null){
            synchronized (ThreadListCache.class) {
                allThread.add(newThread);
            }
        }
    }
    
    /**.
     * 从管理列表中删除
     * @param thread thread
     */
    public static void removeFromList(AbstractThreadOperateImpl thread){
        if (thread != null){
            synchronized (ThreadListCache.class) {
                allThread.remove(thread);
            }
        }
    }
    
    /**.
     * 停止所有的线程
     */
    public static void existAllThread(){
        List<AbstractThreadOperateImpl> tempList = new ArrayList<AbstractThreadOperateImpl>(allThread);
        Iterator<AbstractThreadOperateImpl> iterator = tempList.iterator();
        while (iterator.hasNext()){
            AbstractThreadOperateImpl next = iterator.next();
            next.stopRun();
        }
    }
}
