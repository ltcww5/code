package com.better517na.JavaServiceRouteHelper.business.thread;

/*
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThreadPool.java
 * 修改人：chunfeng
 * 修改时间：2015年4月13日
 * 修改内容：新增
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**.
 * TODO 线程池  专门记录日志的线程池
 * 跟异步更新的线程池分开来 是为了双方不影响
 * 
 * @author     chunfeng
 */
public final class WriteLogThreadPool{
    /**
     * . 单例
     */
    private static volatile WriteLogThreadPool bTaskThreadPool;

    /**
     * 线程池.
     */
    private ExecutorService pool;

    /**
     * 构造函数. 初始化线程池.
     */
    private WriteLogThreadPool() {
        // 初始化20个线程跑
        pool = Executors.newFixedThreadPool(10);
    }

    /**.
     * 
     * TODO 单例模式
     * 
     * @return 当前线程池的引用
     */
    public static WriteLogThreadPool getBTaskThreadPool() {
        if (bTaskThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (bTaskThreadPool == null){
                    bTaskThreadPool = new WriteLogThreadPool();
                }
            }
        }
        return bTaskThreadPool;
    }

    /**
     * 统计批次情况. 加载任务,将任务加载到线程池任务队列中.
     *
     * @param task
     *            加载的任务runnable
     */
    public synchronized void loadTasks(Runnable task) {
        // 加入任务
        pool.execute(task);
    }

    /**
     * 任务完加载完以后关闭线程池，不接受新任务.
     */
    public synchronized void shutdownPool() {
        if (pool != null){
            pool.shutdown();
        }
    }
}
