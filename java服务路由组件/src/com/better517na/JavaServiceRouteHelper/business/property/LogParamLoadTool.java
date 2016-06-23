package com.better517na.JavaServiceRouteHelper.business.property;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * . TODO 用于加载日志配置文件
 * 
 * @author chunfeng
 */
public class LogParamLoadTool extends java.util.Properties {

    /**
     * . 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * . 构造函数.
     */
    public LogParamLoadTool() {
        super();
        try {
            String filePath = PropertiesManager.getConfigPath(PropertiesManager.LOGFILE_NAME);
            // 加载日志的配置文件
            InputStream is = new BufferedInputStream(new FileInputStream(filePath));
            // 副本
            Properties ps = new Properties();
            ps.load(is);

            // 将副本中的值 转化到自己身上来
            LogPropertiesTransport.tranportLogAttribute(this, ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * . 重写父类中的setProperty方法 将传入的null 转化一下
     */
    @Override
    public synchronized Object setProperty(String key, String value) {
        if (value == null) {
            value = "";
        }
        return super.setProperty(key, value);
    }
}
