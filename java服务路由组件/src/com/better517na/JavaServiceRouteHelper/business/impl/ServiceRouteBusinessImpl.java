package com.better517na.JavaServiceRouteHelper.business.impl;

import java.util.ArrayList;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.IServiceRouteBusiness;
import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * @author yishao
 */
public class ServiceRouteBusinessImpl implements IServiceRouteBusiness {

    @Override
    public List<ServiceRouteInfoCache> getServiceAddress(ServiceRouteInfo serviceRoute) {
        List<ServiceRouteInfoCache> serviceRouteInfoCacheList = new ArrayList<ServiceRouteInfoCache>();
        ServiceRouteInfoCache serviceRouteInfoCache = null;

        List<ServiceRouteInfo> serviceAddressList = LoadSpring.getIServiceRouteDaoBean().getServiceAddress(serviceRoute);

        // 设置路由缓存list
        if (serviceAddressList.size() != 0) {
            for (ServiceRouteInfo serviceRouteInfo : serviceAddressList) {
                // 转化model
                serviceRouteInfoCache = new ServiceRouteInfoCache(serviceRouteInfo.getContractName(), serviceRouteInfo.getUniqueSign(), serviceRouteInfo.getBindingType(),
                        serviceRouteInfo.getServiceIP(), serviceRouteInfo.getServicePort(), serviceRouteInfo.getSvcPath(), serviceRouteInfo.getServiceType());
                serviceRouteInfoCacheList.add(serviceRouteInfoCache);
            }
        }
        return serviceRouteInfoCacheList;

    }

    @Override
    public List<ServiceDowntimeInfo> getServiceDowntimeInfo() {
        List<ServiceDowntimeInfo> serviceDowntimeInfo = LoadSpring.getIServiceRouteDaoBean().getServiceDowntimeInfo();
        return serviceDowntimeInfo;
    }
}
