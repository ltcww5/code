package com.better517na.JavaServiceRouteHelper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.dao.BaseDao;
import com.better517na.JavaServiceRouteHelper.dao.IServiceRouteDao;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfo;

/**
 * @author yishao 持久层实现类
 */
public class ServiceRouteDaoImpl extends BaseDao implements IServiceRouteDao {

    @Override
    public List<ServiceRouteInfo> getServiceAddress(ServiceRouteInfo serviceRoute) {

        List<ServiceRouteInfo> serviceRouteInfoList = new ArrayList<ServiceRouteInfo>();
        
        serviceRouteInfoList = LoadSpring.getSqlSessionRead().selectList("com.better517na.JavaServiceRouteHelper.dao.IServiceRouteDao.getServiceAddress", serviceRoute);

        return serviceRouteInfoList;
    }

    @Override
    public List<ServiceDowntimeInfo> getServiceDowntimeInfo() {

        List<ServiceDowntimeInfo> serviceDowntimeInfoList = new ArrayList<ServiceDowntimeInfo>();
        
        serviceDowntimeInfoList = LoadSpring.getSqlSessionRead().selectList("com.better517na.JavaServiceRouteHelper.dao.IServiceRouteDao.getServiceDowntimeInfo");

        return serviceDowntimeInfoList;
    }

}
