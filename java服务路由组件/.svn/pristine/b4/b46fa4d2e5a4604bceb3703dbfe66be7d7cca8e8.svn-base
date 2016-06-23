package com.better517na.JavaServiceRouteHelper.business;

import java.util.List;

import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * @author yishao 业务层
 */
public interface IServiceRouteBusiness {
    /**
     * 获取目标服务地址.
     * 
     * @param serviceRoute
     *            接口和程序唯一标识
     * @return 结果
     */
    public List<ServiceRouteInfoCache> getServiceAddress(
            ServiceRouteInfo serviceRoute);

    /**
     * 获取宕机表信息.
     * 
     * @return 宕机表信息
     * 
     */
    public List<ServiceDowntimeInfo> getServiceDowntimeInfo();
}
