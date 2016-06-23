package com.better517na.JavaServiceRouteHelper.business.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.better517na.JavaServiceRouteHelper.business.IBServiceRouteMath;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * @author yideng
 *
 */
public class PollingMathImpl implements IBServiceRouteMath {
    /**
     * . 单例模式
     */
    public static IBServiceRouteMath instance = new PollingMathImpl();

    /**
     * . 每个契约对应的调用路由计数器
     */
    private ConcurrentHashMap<String, AtomicLong> pollingDic = new ConcurrentHashMap<String, AtomicLong>();

    /**
     * @param serviceRouteList
     *            serviceRouteList
     * @param contract
     *            contract
     * @return 结果
     */
    public ServiceRouteInfoCache getServiceRouteMath(List<ServiceRouteInfoCache> serviceRouteList, String contract) {
        if (serviceRouteList == null || serviceRouteList.size() == 0) {
            return null;
        }

        // 可调用服务路由列表总数
        int count = serviceRouteList.size();

        // 获取调用次数
        AtomicLong invokeTimes = pollingDic.get(contract);
        if (invokeTimes == null){
            pollingDic.put(contract, new AtomicLong(0));
            invokeTimes = pollingDic.get(contract);
        }
        
        //调用次数+1
        long temp = invokeTimes.incrementAndGet();
        int index = (int)(temp % count);
        return serviceRouteList.get(index);
    }
}
