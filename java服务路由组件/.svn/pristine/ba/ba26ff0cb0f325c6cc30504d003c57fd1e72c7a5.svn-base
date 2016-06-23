/*
 * 文件名：StringUtil.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： StringUtil.java
 * 修改人：chunfeng
 * 修改时间：2015年4月9日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * . 字符串处理工具类
 * @author chunfeng
 */
public class StringUtil {
    /**
     * .
     * 
     * TODO 判断一个字符串是否是空值
     * 
     * @param src
     *            入参
     * @return boolean
     */
    public static boolean isNullOrEmpty(final String src) {
        if (src == null) {
            return true;
        }
        if ("".equals(src.trim())) {
            return true;
        }

        return false;
    }

    /**
     * .
     * 
     * TODO 替换转义字符 如:&quot;-> "
     * 
     * @param src
     *            src
     * @return 处理好的结果
     */
    public static String replaceESCStr(final String src) {
        // 默认返回值
        String result = "";

        result = src.replace("&quot;", "\"");
        result = result.replace("&amp;", "&");
        result = result.replace("&lt;", "<");
        result = result.replace("&gt;", ">");
        result = result.replace("&nbsp;", " ");

        return result.trim();
    }

    /**
     * . 获取异常的具体的异常信息
     * 
     * @param ex
     *            异常对象
     * @return 异常信息字符串
     */
    public static String getExceptionDetailStr(final Exception ex) {
        if (ex == null) {
            return "";
        }
        // 定义bytearray对象 
        ByteArrayOutputStream buf = null;
        try {
            buf = new java.io.ByteArrayOutputStream();
            ex.printStackTrace(new java.io.PrintWriter(buf, true));
            return buf.toString();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
    
    /**.
     * 获取对应的字符串表达式
     *  
     * @param pval 对象
     * @return 字符串
     */
    public static String getPropertyStr(Object pval){
        if (pval == null){
            return "";
        }
        return String.valueOf(pval);
    }
}
