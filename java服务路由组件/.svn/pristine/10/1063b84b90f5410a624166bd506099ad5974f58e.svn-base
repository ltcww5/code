/*
 * 文件名：LogPropertiesTransport.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogPropertiesTransport.java
 * 修改人：chunfeng
 * 修改时间：2015年4月15日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.property;

import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * . 转化读取的配置文件属性
 * 
 * @author chunfeng
 */
public class LogPropertiesTransport {

    /**
     * . 转换properties属性的工具方法
     * 
     * @param dest
     *            dest
     * @param src
     *            src
     */
    public static void tranportLogAttribute(Properties dest, Properties src) {
        // 防御性判空
        if (dest == null || src == null) {
            throw new InvalidParameterException("进行转化的properties对象不能为空");
        }

        // 属性转换
        dest.setProperty("AppName", src.getProperty("log.AppName"));
        dest.setProperty("AppRecord", src.getProperty("log.AppRecord"));

        dest.setProperty("DebugRecord", src.getProperty("log.DebugRecord"));
        dest.setProperty("jdbc.driver", src.getProperty("log.jdbc.driver"));
        dest.setProperty("downLoadDiretory", src.getProperty("log.download.Directory"));

        dest.setProperty("AccUrl", src.getProperty("log.acc.jdbc.url"));
        dest.setProperty("AccUserName", src.getProperty("log.acc.jdbc.username"));
        dest.setProperty("AccPassword", src.getProperty("log.acc.jdbc.password"));
        dest.setProperty("AppUrl", src.getProperty("log.app.jdbc.url"));
        dest.setProperty("AppUserName", src.getProperty("log.app.jdbc.username"));
        dest.setProperty("AppPassword", src.getProperty("log.app.jdbc.password"));

        dest.setProperty("CacheHitUrl", src.getProperty("log.cachehit.jdbc.url"));
        dest.setProperty("CacheHitUserName", src.getProperty("log.cachehit.jdbc.username"));
        dest.setProperty("CacheHitPassword", src.getProperty("log.cachehit.jdbc.password"));

        dest.setProperty("DebugUrl", src.getProperty("log.debug.jdbc.url"));
        dest.setProperty("DebugUserName", src.getProperty("log.debug.jdbc.username"));
        dest.setProperty("DebugPassword", src.getProperty("log.debug.jdbc.password"));

        dest.setProperty("ExceptionUrl", src.getProperty("log.exception.jdbc.url"));
        dest.setProperty("ExceptionUserName", src.getProperty("log.exception.jdbc.username"));
        dest.setProperty("ExceptionPassword", src.getProperty("log.exception.jdbc.password"));

        dest.setProperty("InteractionUrl", src.getProperty("log.interaction.jdbc.url"));
        dest.setProperty("InteractionUserName", src.getProperty("log.interaction.jdbc.username"));
        dest.setProperty("InteractionPassword", src.getProperty("log.interaction.jdbc.password"));

        dest.setProperty("SlowCallUrl", src.getProperty("log.slowcall.jdbc.url"));
        dest.setProperty("SlowCallUserName", src.getProperty("log.slowcall.jdbc.username"));
        dest.setProperty("SlowCallPassword", src.getProperty("log.slowcall.jdbc.password"));

        dest.setProperty("UiaccUrl", src.getProperty("log.uiacc.jdbc.url"));
        dest.setProperty("UiaccUserName", src.getProperty("log.uiacc.jdbc.username"));
        dest.setProperty("UiaccPassword", src.getProperty("log.uiacc.jdbc.password"));

        dest.setProperty("Directory", src.getProperty("log.Directory"));
        dest.setProperty("DivisionFileSize", src.getProperty("log.DivisionFileSize"));
        dest.setProperty("DivisionFileTime", src.getProperty("log.DivisionFileTime"));
    }
}
