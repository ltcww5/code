package com.better517na.JavaServiceRouteHelper.business.Cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceDowntimeInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * 排除异常，宕机的列表.
 * 
 * @author yishao
 *
 */
public class ExceptionCheck {
    /**
     * 移除比例，大于等于3个是按照35排除.
     */
    private static double removeRate = 0.35;

    /**
     * 每一个服务路由组件一个异常调用服务的待检测列表. key = > value ip+port => 宕机的服务信息
     */
    public static ConcurrentMap<String, ServiceRouteInfoCache> checkList = new ConcurrentHashMap<String, ServiceRouteInfoCache>();

    /**
     * 
     * @param serviceRouteInfoCacheList
     *            路由列表
     * @return 排除宕机后的路由列表
     */
    public static List<ServiceRouteInfoCache> removeDowntimeInfo(List<ServiceRouteInfoCache> serviceRouteInfoCacheList) {

        // 宕机列表
        List<ServiceDowntimeInfo> serviceDowntimeInfoList = new ArrayList<ServiceDowntimeInfo>();
        // 删除列表
        List<ServiceRouteInfoCache> serviceRouteInfo = new ArrayList<ServiceRouteInfoCache>();

        // 默认值
        double removeRateTemp = removeRate;

        // 路由个数
        double serviceRouteCount = 0;

        // 判断一共配置了多少个路由，大于等于3个负载的按35%排除，小于等于2个负载的按50%排除
        if (serviceRouteInfoCacheList != null) {
            serviceRouteCount = serviceRouteInfoCacheList.size();
        }

        // 少于2个的数量 则按50%计算
        if (serviceRouteCount <= 2) {
            removeRateTemp = 0.5;
        }

        // 判断是否到达阀值
        boolean isthreshold = false;

        // 从宕机缓存中获取宕机列表
        ServiceCacheModel<ServiceDowntimeInfo> downTimeValue = ServiceRouteCacheManager.getServiceDowntimeInfoCache();

        // 缓存中有数据 则直接使用
        if (downTimeValue != null && downTimeValue.getCacheList().size() != 0) {

            serviceDowntimeInfoList = downTimeValue.getCacheList();

            // 不用去更新 有额外线程直接在做更新操作

        } else {
            // 第一次请求，缓存为空 则不管宕机列表
            serviceDowntimeInfoList = new ArrayList<ServiceDowntimeInfo>();

            // serviceDowntimeInfoList = LoadSpring.getIServiceRouteServiceBean().getServiceDowntimeInfo();

            // if (serviceDowntimeInfoList != null) {
            // ServiceCacheModel<ServiceDowntimeInfo> newCache = new ServiceCacheModel<ServiceDowntimeInfo>();
            // newCache.setCacheList(serviceDowntimeInfoList);
            // newCache.setCacheTime(new Date());
            // // 更新缓存
            // ServiceRouteCacheManager.setServiceDowntimeInfoCache(newCache);
            // }
        }

        // 路由列表不为空才进行
        if (serviceRouteCount > 0) {
            double removeNum = 0;
            // 排除已宕机的服务
            if (serviceDowntimeInfoList != null && serviceDowntimeInfoList.size() > 0) {
                // 获取所有宕机服务的ip列表
                List<String> iplist = new ArrayList<String>();
                for (ServiceDowntimeInfo down : serviceDowntimeInfoList) {
                    iplist.add(down.getiP());
                }

                // 遍历所有的路由
                for (ServiceRouteInfoCache serviceRouteInfoCache : serviceRouteInfoCacheList) {
                    if (iplist.contains(serviceRouteInfoCache.getServiceIP())) {
                        removeNum++;
                        if ((removeNum / serviceRouteCount) > removeRateTemp) {
                            isthreshold = true;
                            break;
                        } else {
                            // 加入删除列表
                            serviceRouteInfo.add(serviceRouteInfoCache);
                        }
                    }
                }
                // 删除已宕机的
                serviceRouteInfoCacheList.removeAll(serviceRouteInfo);
            }
            // 未到达阀值
            if (!isthreshold) {
                // 排除异常列表
                if (checkList.size() > 0) {

                    serviceRouteInfo.clear();
                    for (ServiceRouteInfoCache serviceRoute : serviceRouteInfoCacheList) {
                        String ckey = generateExceptionKey(serviceRoute);
                        if (ckey == null) {
                            continue;
                        }

                        if (checkList.keySet().contains(ckey)) {
                            removeNum++;
                            if ((removeNum / serviceRouteCount) > removeRateTemp) {
                                // 大于移除比例，不移除,同时退出循环
                                isthreshold = true;
                                break;
                            } else {
                                // 加入删除列表
                                serviceRouteInfo.add(serviceRoute);
                            }
                        }
                    }
                    // 排除异常列表
                    serviceRouteInfoCacheList.removeAll(serviceRouteInfo);
                }
            }
        } else {
            return serviceRouteInfoCacheList;
        }
        return serviceRouteInfoCacheList;
    }

    /**
     * 更新检测列表.
     * 
     * @param checkCache
     *            异常路由
     * @param updateType
     *            异常更新类型 0：添加，1：移除
     */
    public static void updateCheckList(ServiceRouteInfoCache checkCache, int updateType) {
        // 添加
        if (updateType == 0) {

            String key = generateExceptionKey(checkCache);

            // 判空处理
            if (key == null) {
                return;
            }
            checkList.put(key, checkCache);
        } else if (updateType == 1) {
            // 移除
            String key = generateExceptionKey(checkCache);

            if (checkList.containsKey(key)) {
                checkList.remove(key);
            }
        }
    }

    /**
     * .
     * 
     * TODO 生成每一个异常服务的key值 ip+port
     * 
     * @param o
     *            生成key的对象
     * @return key
     */
    public static String generateExceptionKey(ServiceRouteInfoCache o) {
        // 判空处理
        if (o == null) {
            return null;
        }
        return o.getServiceIP() + "_" + o.getServicePort();
    }

}
