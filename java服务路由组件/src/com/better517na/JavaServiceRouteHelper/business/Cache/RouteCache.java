package com.better517na.JavaServiceRouteHelper.business.Cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.better517na.JavaServiceRouteHelper.business.property.PropertiesManager;
import com.better517na.JavaServiceRouteHelper.business.spring.LoadSpring;
import com.better517na.JavaServiceRouteHelper.model.ServiceCacheModel;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfo;
import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;

/**
 * @author yishao 缓存处理
 */
public class RouteCache {
    /**
     * 程序唯一标识.
     */
    public static String uniqueSign;

    /**
     * . cxf连接timeout
     */
    public static String cxfConnectionTimeout;

    /**
     * . cxf读timeout
     */
    public static String cxfReadTimeout;

    /**
     * . NANO=186 机房编号
     */
    public static String nanoValue;

    /**
     * . DTCT=NW 数据中心
     */
    public static String dtctValue;

    /**
     * . 是否为debug模式
     */
    public static boolean isDebug = false;

    /**
     * . 赋值
     */
    static {
        Map<String, String> values = PropertiesManager.getNANODTCT();
        nanoValue = values.get(PropertiesManager.NANO_TAG);
        dtctValue = values.get(PropertiesManager.DTCT_TAG);

        // serviceRoute配置文件
        values = PropertiesManager.getServiceRouteProperties();
        uniqueSign = values.get(PropertiesManager.UNIQUESIGN_TAG);
        cxfConnectionTimeout = values.get(PropertiesManager.CXFCONNECTIONTIME_TAG);
        cxfReadTimeout = values.get(PropertiesManager.CXFREADTIME_TAG);
        isDebug = "1".equals(values.get(PropertiesManager.DEBUGMODEL_TAG)) ? true : false;
    }

    /**
     * 客户端请求连接服务时，先去找缓存，如缓存为空或缓存过期，才从数据库中获取 （默认缓存时间为2分钟，过期无效）..
     * 
     * @param serviceClass
     *            接口名
     * @param <T>
     *            类型
     * @return List<ServiceRouteInfoCache>
     */
    public static <T> List<ServiceRouteInfoCache> getRouteCache(Class<T> serviceClass) {
        String contractName = serviceClass.getName();

        // 用接口名和程序唯一标识作为可用路由缓存list的key
        String mapKey = generateMapKey();

        // 无论缓存是否过期，都先从缓存中获取数据
        ServiceCacheModel<ServiceRouteInfoCache> cacheModel = ServiceRouteCacheManager.getServiceRouteCache(mapKey);

        // 返回的默认值
        List<ServiceRouteInfoCache> cacheList = new ArrayList<ServiceRouteInfoCache>();

        // 拿到所有的路由信息
        if (cacheModel != null && cacheModel.getCacheList().size() > 0) {
            cacheList = cacheModel.getCacheList();
        } else {
            // 从数据库中拿到 同步更新缓存
            cacheList = getAvailableRouteCacheList();
        }

        // 获取需要的服务 根据contractName 去匹配
        cacheList = getListByContractName(cacheList, contractName);

        // 去除掉宕机和异常的
        cacheList = ExceptionCheck.removeDowntimeInfo(cacheList);

        return cacheList;
    }

    /**
     * . 同步从数据库中获取可用的服务列表 并加入缓存
     * 
     * @return 可用服务路由列表
     */
    public static List<ServiceRouteInfoCache> getAvailableRouteCacheList() {
        // 没有缓存,查询路由库
        List<ServiceRouteInfoCache> routeCacheList = getServiceRouteInfoCache();

        // 如果有值 则往缓存中加入数据
        if (routeCacheList != null && routeCacheList.size() > 0) {
            ServiceCacheModel<ServiceRouteInfoCache> value = new ServiceCacheModel<ServiceRouteInfoCache>();
            value.setCacheList(routeCacheList);
            value.setCacheTime(new Date());
            // 加入路由缓存列表
            ServiceRouteCacheManager.setServiceRouteCache(generateMapKey(), value);
        }
        return routeCacheList;
    }

    /**
     * 从路由库中查询获取.
     * 
     * @return List<ServiceRouteInfoCache>
     */
    public static List<ServiceRouteInfoCache> getServiceRouteInfoCache() {
        // 创建路由列表，用于接收从路由库中查询到的路由数据
        List<ServiceRouteInfoCache> routeCacheList = new ArrayList<ServiceRouteInfoCache>();

        // 创建ServiceRouteInfo对象
        ServiceRouteInfo serviceRouteInfo = new ServiceRouteInfo(uniqueSign, dtctValue, nanoValue);

        routeCacheList = LoadSpring.getIServiceRouteServiceBean().getServiceAddress(JsonUtil.toJson(serviceRouteInfo));

        return routeCacheList;

    }

    /**
     * 获取目标服务url.
     * 
     * @param serviceRouteInfo
     *            路由信息
     * @return url
     */
    public static String getServiceUrl(ServiceRouteInfoCache serviceRouteInfo) {
        // 定义绑定类型、服务IP,服务端口，服务路径
        String bindingType = "";
        String serviceIP = "";
        String svcPath = "";
        int servicePort;

        // 拼接的地址
        String urlString = "";

        // 不为空则进行地址拼接
        if (serviceRouteInfo != null) {
            // 获取绑定类型、服务IP,服务端口，服务路径
            bindingType = serviceRouteInfo.getBindingType();
            serviceIP = serviceRouteInfo.getServiceIP();
            servicePort = serviceRouteInfo.getServicePort();
            svcPath = serviceRouteInfo.getSvcPath();
            // 如果端口不存在
            if (servicePort <= 0) {
                // 域名+SvcPath
                urlString = serviceIP.trim() + "/" + svcPath.trim();
            } else {
                // BindingType://ServiceIP:ServicePort/SvcPath（服务名+客户端访问服务端的地址)
                // int类型端口转string
                String port = Integer.toString(servicePort);
                // 拼接地址
                urlString = bindingType.trim().toLowerCase() + "://" + serviceIP.trim() + ":" + port.trim() + "/" + svcPath.trim();
            }
        }

        return urlString;
    }

    /**
     * . 添加方法注释.
     * 
     * @param src
     *            筛选的来源
     * @param contractName
     *            契约名称
     * @return 符合条件的列表
     */
    public static List<ServiceRouteInfoCache> getListByContractName(List<ServiceRouteInfoCache> src, String contractName) {
        // 默认值
        List<ServiceRouteInfoCache> list = new ArrayList<ServiceRouteInfoCache>();

        // 防御性判空
        if (src == null || src.size() == 0 || contractName == null || "".equals(contractName)) {
            return list;
        }

        for (ServiceRouteInfoCache sri : src) {
            // 防御性的判断
            if (sri.getContractName() == null) {
                continue;
            }
            if (contractName.equalsIgnoreCase(sri.getContractName())) {
                list.add(sri);
            }
        }

        return list;
    }

    /**
     * . 公用方法 用于生成缓存的key
     * 
     * @return mapkey
     */
    public static String generateMapKey() {
        StringBuilder bs = new StringBuilder();

        bs.append(nanoValue);
        bs.append("_");
        bs.append(dtctValue);
        bs.append("_");
        bs.append(uniqueSign);

        return bs.toString();
    }
}
