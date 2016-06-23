package com.better517na.JavaServiceRouteHelper.business;

import java.util.List;

import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**.
 * 路由算法实现
 * @author chunfeng
 *
 */
public interface IBServiceRouteMath {
    /**
     * @param serviceRouteList 路由列表
     * @param contract 契约名
     * @return 返回路由信息
     */
    public ServiceRouteInfoCache getServiceRouteMath(List<ServiceRouteInfoCache> serviceRouteList, String contract);
}
