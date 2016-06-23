/*
 * 文件名：OperateHttpData.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： OperateHttpData.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.operateData;

/**.
 * TODO 处理cxf调用的前后数据
 * 
 * @author     chunfeng
 */
public interface OperateHttpData {
    
    /**.
     * 
     * TODO cxf 方法调用前执行的方法  
     * 
     * @param content request流中的数据
     * @param clientIp 调用服务的ip
     */
    void beforeInvoke(String content, String clientIp);
    
    /**.
     * TODO cxf 方法调用之后执行的处理方法
     * 
     * @param param request流中的数据
     * @param result response流中的数据
     * @param clientIp 调用服务的ip
     */
    void afterInvoke(String param, String result, String clientIp);
    
}
