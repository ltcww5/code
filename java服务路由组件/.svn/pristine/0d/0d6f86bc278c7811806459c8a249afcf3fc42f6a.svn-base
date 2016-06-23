package com.better517na.JavaServiceRouteHelper.business.Cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.better517na.JavaServiceRouteHelper.business.property.PropertiesManager;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * @author chunfeng 
 * acclog 记录相关配置参数
 */
public class AccLogWriteConfig {
    /**
     * acclog 记录开关.
     */
    private static boolean writeAccLog = true;

    /**
     * . 记录acc日志的方法名列表
     */
    private static Set<String> writeAccLogMethodes = new HashSet<String>();

    /**
     * . 不记录acc日志的方法名列表
     */
    private static Set<String> noAccLogMethodes  = new HashSet<String>();

    /**
     * . 赋值
     */
    static {
        // serviceRoute配置文件
        Map<String, String> values = PropertiesManager.getServiceRouteProperties();
        String tswitch = values.get(PropertiesManager.ACCLOG_SWITCH_TAG);
        if (!StringUtil.isNullOrEmpty(tswitch) && !"1".equals(tswitch)){
            writeAccLog = false; 
        }
        
        String writeLogMethodes = values.get(PropertiesManager.WRITEACCLOG_METHODES_TAG);
        if (!StringUtil.isNullOrEmpty(writeLogMethodes)){
            String[] tmethodes = writeLogMethodes.split(",");
            for (int i = 0, len = tmethodes.length; i < len; i++){
                writeAccLogMethodes.add(tmethodes[i].trim());
            }
        }
        
        String noLogMethodes = values.get(PropertiesManager.NOACCLOG_METHODES_TAG);
        if (!StringUtil.isNullOrEmpty(noLogMethodes)){
            String[] tmethodes = noLogMethodes.split(",");
            for (int i = 0, len = tmethodes.length; i < len; i++){
                noAccLogMethodes.add(tmethodes[i].trim());
            }
        }
    }
    
    /**.
     * 判断该方法是否要记录acc日志
     * @param methodName 方法名
     * @return 是否记录acc日志
     */
    public static boolean writeAccLog(final String methodName){
        // 默认行为  要记录日志
        if (StringUtil.isNullOrEmpty(methodName)){
            return true;
        }
        
        // 拷贝名称
        String tmname = methodName;
        
        if (writeAccLog){
            if (noAccLogMethodes.contains(tmname)){
                return false; 
            }
            return true;
        } else {
            if (writeAccLogMethodes.contains(tmname)){
                return true; 
            }
            return false;
        }
    }
}
