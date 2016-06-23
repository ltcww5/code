/*
 * 文件名：ThreadLocalLogAttach.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThreadLocalLogAttach.java
 * 修改人：chunfeng
 * 修改时间：2015年11月4日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.log;

import java.util.Date;

/**
 * 用于辅助日志记录.
 * @author     chunfeng
 */
public class ThreadLocalLogAttach {
    /**.
     * 每一个线程一个记录时间点的辅助对象
     */
    private static ThreadLocal<Long> timeStampAttach = new ThreadLocal<Long>(){
        @Override
        public Long initialValue(){
            return new Date().getTime();
        }
    };
    
    /**.
     * 标识是否记录acc日志
     */
    private static ThreadLocal<Boolean> writeAccLogAttach = new ThreadLocal<Boolean>(){
        @Override
        public Boolean initialValue(){
            return new Boolean(true);
        }
    };
    
    /**.
     * 反之标识表名是否记录acc日志
     * 
     * @return 是否记录
     */
    public static boolean wirteAccLog(){
        return writeAccLogAttach.get();
    }
    
    /**.
     * 设置是否记录acclog
     * @param writeAccLog 是否记录
     */
    public static void setWriteAccLog(boolean writeAccLog){
        writeAccLogAttach.set(new Boolean(writeAccLog));
    }
    
    /**.
     * 获取当前线程的时间点.
     * 
     * @return 时间
     */
    public static long getTimeStamp(){
        return timeStampAttach.get();
    }
    
    /**.
     * 设置当前线程新的timestamp值
     * 
     * @param newTimeStamp 新的时间点
     */
    public static void setNewTimeStamp(long newTimeStamp){
        timeStampAttach.set(newTimeStamp);
    }
}
