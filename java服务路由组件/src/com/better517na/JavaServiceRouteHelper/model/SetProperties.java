package com.better517na.JavaServiceRouteHelper.model;

import java.io.Serializable;

/**
 * ServiceRoute.properties文件对应属性.
 * 
 * @author yishao
 *
 */
public class SetProperties implements Serializable {

    /**
     * . 序列号
     */
    private static final long serialVersionUID = 563441563666490926L;

    /**
     * driver.
     */
    private String driver;

    /**
     * url.
     */
    private String url;

    /**
     * username.
     */
    private String username;

    /**
     * password.
     */
    private String password;

    /**
     * 设置driver.
     * 
     * @return 返回driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * 获取driver.
     * 
     * @param driver
     *            要设置的driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * 设置url.
     * 
     * @return 返回url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取url.
     * 
     * @param url
     *            要设置的url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置username.
     * 
     * @return 返回username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取username.
     * 
     * @param username
     *            要设置的username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 设置password.
     * 
     * @return 返回password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取password.
     * 
     * @param password
     *            要设置的password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
