package com.better517na.JavaServiceRouteHelper.service.impl;

import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.JavaServiceRouteHelper.service.IServiceRouteService;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;

/**
 * @author yishao
 *
 */
public class ServiceRouteServiceImpl implements IServiceRouteService {

    @Override
    public List<ServiceRouteInfoCache> getServiceAddress(String serviceRouteInfo) {
        try {
            // 转json
            ServiceRouteInfo serviceRoute = JsonUtil.toObject(serviceRouteInfo, ServiceRouteInfo.class);
            List<ServiceRouteInfoCache> serviceRouteInfoCacheList = LoadSpring.getIServiceRouteBusinessBean().getServiceAddress(serviceRoute);

            // 返回结果
            return serviceRouteInfoCacheList;
        } catch (Exception e) {
            // 打印出来 好定位问题
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ServiceDowntimeInfo> getServiceDowntimeInfo() {
        try {
            List<ServiceDowntimeInfo> serviceDowntimeInfo = LoadSpring.getIServiceRouteBusinessBean().getServiceDowntimeInfo();
            return serviceDowntimeInfo;
        } catch (Exception e) {
            // 打印出来 好定位问题
            e.printStackTrace();
            return null;
        }
    }

}
