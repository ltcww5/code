/*
 * 文件名：ServiceCacheModel.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ServiceCacheModel.java
 * 修改人：chunfeng
 * 修改时间：2015年4月10日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * . TODO 缓存的存储model
 * 
 * @param <T>
 *            类型
 * @author chunfeng
 */
public class ServiceCacheModel<T> implements Serializable {
    /**
     * 添加字段注释.
     */
    private static final long serialVersionUID = 1L;

    /**
     * . 缓存的信息列表
     */
    private List<T> cacheList;

    /**
     * . 缓存的时间
     */
    private Date cacheTime;

    /**
     * . 构造函数.
     *
     */
    public ServiceCacheModel() {
        setCacheList(new ArrayList<T>());
        setCacheTime(new Date());
    }

    /**
     * 设置cacheList.
     * 
     * @return 返回cacheList
     */
    public List<T> getCacheList() {
        return cacheList;
    }

    /**
     * 获取cacheList.
     * 
     * @param cacheList
     *            要设置的cacheList
     */
    public void setCacheList(List<T> cacheList) {
        this.cacheList = cacheList;
    }

    /**
     * 设置cacheTime.
     * 
     * @return 返回cacheTime
     */
    public Date getCacheTime() {
        return cacheTime;
    }

    /**
     * 获取cacheTime.
     * 
     * @param cacheTime
     *            要设置的cacheTime
     */
    public void setCacheTime(Date cacheTime) {
        this.cacheTime = cacheTime;
    }

}
