/*
 * 文件名：SystemTool.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SystemTool.java
 * 修改人：chunfeng
 * 修改时间：2015年4月20日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**.
 * 系统工具类
 * @author     chunfeng
 */
public class SystemTool {
    
    /**.
     * 本地ip地址变量
     */
    public static String localIp;
    
    /**.
     * 本地机器名
     */
    public static String localMachineName;
    
    static{
        try {
            localIp = InetAddress.getLocalHost().getHostAddress().toString().trim();
            localMachineName = InetAddress.getLocalHost().getHostName().toString().trim();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    
}
