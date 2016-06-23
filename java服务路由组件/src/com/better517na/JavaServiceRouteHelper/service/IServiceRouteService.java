package com.better517na.JavaServiceRouteHelper.service;

import java.util.List;

import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * 
 * @author yishao
 *
 */
public interface IServiceRouteService {

    /**
     * 获取目标服务地址.
     * 
     * @param serviceRouteInfo
     *            接口和程序唯一标识
     * @return 结果
     */
    public List<ServiceRouteInfoCache> getServiceAddress(String serviceRouteInfo);

    /**
     * 获取宕机表信息.
     * 
     * @return 宕机表信息
     */
    public List<ServiceDowntimeInfo> getServiceDowntimeInfo();
}
