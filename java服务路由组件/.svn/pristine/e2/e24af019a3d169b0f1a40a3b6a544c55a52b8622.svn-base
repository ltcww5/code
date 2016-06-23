package com.better517na.JavaServiceRouteHelper.business.property;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * . 属性导入
 * 
 * @author yishao
 *
 */
public class PropertiesManager {

    /**
     * . jdbc.properties 文件名
     */
    public static final String JDBCCONFIG_FILENAME = "jdbc.properties";

    /**
     * . 服务路由配置文件的文件名
     */
    public static final String SERVICEROUTE_FILENAME = "ServiceRoute.properties";

    /**
     * . 日志配置文件的名称
     */
    public static final String LOGFILE_NAME = "log_config.properties";

    /**
     * . jdbc中配置文件中nanodct的key的名字
     */
    public static final String NANODTCT_TAG = "keyIdConfigPath";

    /**
     * . 常量 uniqueSign
     */
    public static final String UNIQUESIGN_TAG = "uniqueSign";

    /**
     * . 常量 debug.model
     */
    public static final String DEBUGMODEL_TAG = "debug.model";

    /**
     * . 重试次数
     */
    public static final String RETRYNUM_TAG = "retrynum";

    /**
     * . 常量 cxf.connectionTimeout
     */
    public static final String CXFCONNECTIONTIME_TAG = "cxf.connectionTimeout";

    /**
     * . 常量 cxf.readtimeout
     */
    public static final String CXFREADTIME_TAG = "cxf.readtimeout";

    /**
     * . 常量 NANO
     */
    public static final String NANO_TAG = "NANO";

    /**
     * . 常量 DTCT
     */
    public static final String DTCT_TAG = "DTCT";

    /**
     * . ************************acc日志记录相关参数*****************************************
     */
    /**
     * . acc日志记录总开关
     */
    public static final String ACCLOG_SWITCH_TAG = "acclog.switch";

    /**
     * . 记录acc日志的方法名列表
     */
    public static final String WRITEACCLOG_METHODES_TAG = "acclog.method.writelog";

    /**
     * . 不记录acc日志的方法名列表
     */
    public static final String NOACCLOG_METHODES_TAG = "acclog.method.nolog";

    /**
     * 处理器类名.
     */
    public static String handlerClassName;

    /**
     * . 寻找属性文件的路径
     * 
     * @param fileName
     *            文件名称
     * @return 文件路径
     */
    public static String getConfigPath(String fileName) {
        String jarPath = PropertiesManager.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String filePath = (new File(jarPath)).getParentFile().getAbsolutePath() + "/" + "config" + "/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            filePath = (new File(jarPath)).getParentFile().getParentFile().getAbsolutePath() + "/" + "config" + "/" + fileName;
        }
        // 固定路径 位于与lib包同级的config下面
        // String filePath = (new File(jarPath)).getParentFile().getParentFile().getAbsolutePath() + "\\" + "config" + "\\" + fileName;
        //
        // filePath = filePath.replace('\\', '/');

        return filePath;
    }

    /**
     * . 获取serviceRoute配置文件内容
     * 
     * @return properties
     */
    public static Map<String, String> getServiceRouteProperties() {
        /**
         * 获取服务路由配置文件路径
         */
        String propertiesPath = getConfigPath(SERVICEROUTE_FILENAME);
        Properties loadProperties = new Properties();
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(propertiesPath));
            loadProperties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("确定文件:" + propertiesPath + " 是否存在");
        }

        // 返回的map
        Map<String, String> values = new HashMap<String, String>();
        values.put(UNIQUESIGN_TAG, StringUtil.getPropertyStr(loadProperties.get(UNIQUESIGN_TAG)));
        values.put(CXFCONNECTIONTIME_TAG, StringUtil.getPropertyStr(loadProperties.get(CXFCONNECTIONTIME_TAG)));
        values.put(CXFREADTIME_TAG, StringUtil.getPropertyStr(loadProperties.get(CXFREADTIME_TAG)));
        values.put(DEBUGMODEL_TAG, StringUtil.getPropertyStr(loadProperties.get(DEBUGMODEL_TAG)));
        values.put(RETRYNUM_TAG, StringUtil.getPropertyStr(loadProperties.get(RETRYNUM_TAG)));

        // acc日志记录相关
        values.put(ACCLOG_SWITCH_TAG, StringUtil.getPropertyStr(loadProperties.get(ACCLOG_SWITCH_TAG)));
        values.put(WRITEACCLOG_METHODES_TAG, StringUtil.getPropertyStr(loadProperties.get(WRITEACCLOG_METHODES_TAG)));
        values.put(NOACCLOG_METHODES_TAG, StringUtil.getPropertyStr(loadProperties.get(NOACCLOG_METHODES_TAG)));

        handlerClassName = StringUtil.getPropertyStr(loadProperties.get("service.handlerClassName"));
        return values;
    }

    /**
     * TODO NANODTCT.
     * 
     * @return NANODTCT
     */
    public static Map<String, String> getNANODTCT() {

        Map<String, String> values = new HashMap<String, String>();
        Properties loadProperty = new Properties();
        // 获取jdbc.properties 配置文件的路径
        String jdbcPath = getConfigPath(JDBCCONFIG_FILENAME);
        InputStream is = null;
        // 获取路径
        try {
            is = new BufferedInputStream(new FileInputStream(jdbcPath));
            loadProperty.load(is);
        } catch (Exception e) {
            throw new RuntimeException("确定文件:" + jdbcPath + " 是否存在");
        }

        // 获取NANODTCT 配置文件的路径
        String nandtctPath = loadProperty.getProperty(NANODTCT_TAG);
        if (nandtctPath != null && !"".equals(nandtctPath)) {
            try {
                is = new BufferedInputStream(new FileInputStream(nandtctPath));
                loadProperty.load(is);
                values.put(NANO_TAG, StringUtil.getPropertyStr(loadProperty.get(NANO_TAG)));
                values.put(DTCT_TAG, StringUtil.getPropertyStr(loadProperty.get(DTCT_TAG)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
