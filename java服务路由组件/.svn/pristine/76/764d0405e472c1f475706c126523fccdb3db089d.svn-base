/*
 * 文件名：OperateHttpDataImpFactory.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： OperateHttpDataImpFactory.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.operateData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.better517na.JavaServiceRouteHelper.business.servlet.operateData.impl.LogAttach;

/**.
 * TODO 简单工厂方法  用于进行servlte数据处理的对象
 * 
 * @author     chunfeng
 */
public final class OperateHttpDataImpFactory {
    
    /**.
     * 单例模式对象
     */
    private static OperateHttpDataImpFactory instance;
    
    /**.
     * 存放所有的实现对象
     */
    private volatile ConcurrentMap<Integer, OperateHttpData>  allImplement;
    
    /**.
     * 
     * 私有构造函数.
     *
     */
    private OperateHttpDataImpFactory(){
        allImplement = new ConcurrentHashMap<Integer, OperateHttpData>();
    }
    
    /**.
     * TODO 单例模式返回对象
     * @return 单例模式对象
     */
    public static OperateHttpDataImpFactory getInstance(){
        if (instance == null) {
           synchronized (OperateHttpDataImpFactory.class) {
                if (instance == null) {
                    instance = new OperateHttpDataImpFactory();
                }
            } 
        }
        return instance;
    }
    
    /**.
     * 
     * TODO 对外暴露的获取实现的接口方法
     * 
     * @param eum eum
     * @return 获取实现
     */
    public OperateHttpData getOperateImp(int eum){
        OperateHttpData impl = allImplement.get(eum);
        if (impl == null){
            synchronized (OperateHttpDataImpFactory.class) {
                if (impl == null) {
                    impl = createImpl(eum);
                    allImplement.put(eum, impl);
                }
            }
        }
        return impl;
    }
    
    /**.
     * TODO 创建实现类
     * @param eum eum
     * @return 实现类
     */
    private OperateHttpData createImpl(int eum){
        switch(eum) {
            case OperateImplEnum.LOGANDATTACH:
                return new LogAttach();
            default:
                //默认实现
                return new LogAttach();
        }
    }
    
}
