package com.better517na.JavaServiceRouteHelper.business.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import com.Better517na.EncryptDecrypt.BEncryptDecrypt;
import com.better517na.JavaServiceRouteHelper.business.property.PropertiesManager;
import com.better517na.JavaServiceRouteHelper.model.SetProperties;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * @author yishao
 */
public class DataSourceMapper extends BasicDataSource {

    /**
     * . 构造函数.
     */
    public DataSourceMapper() {
        super();
        String configPath = PropertiesManager.getConfigPath(PropertiesManager.SERVICEROUTE_FILENAME);
        SetProperties serviceProperties = getProperties(configPath);

        this.setDriverClassName(serviceProperties.getDriver());
        this.setUrl(serviceProperties.getUrl());
        this.setUsername(serviceProperties.getUsername());
        this.setPassword(serviceProperties.getPassword());
    }

    /**
     * @param propertiesPath
     *            ServiceRoute.properties
     * @return SetProperties 结果
     */
    private static SetProperties getProperties(String propertiesPath) {

        SetProperties setProperties = new SetProperties();
        InputStream is = null;
        Properties loadProperties = new Properties();
        
        try {
            is = new BufferedInputStream(new FileInputStream(propertiesPath));
            loadProperties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("确定文件:"+propertiesPath+" 是否存在");
        }

        String userName = BEncryptDecrypt.GetDecryptStrOld(StringUtil.getPropertyStr(loadProperties.getProperty("username")));
        String passWord = BEncryptDecrypt.GetDecryptStrOld(StringUtil.getPropertyStr(loadProperties.getProperty("password")));

        loadProperties.setProperty("username", userName);
        loadProperties.setProperty("password", passWord);

        setProperties.setDriver(StringUtil.getPropertyStr(loadProperties.getProperty("driver")));
        setProperties.setUrl(StringUtil.getPropertyStr(loadProperties.getProperty("url")));
        setProperties.setUsername(StringUtil.getPropertyStr(loadProperties.getProperty("username")));
        setProperties.setPassword(StringUtil.getPropertyStr(loadProperties.getProperty("password")));

        return setProperties;
    }

}
