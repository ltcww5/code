package com.better517na.JavaServiceRouteHelper.business.servlet;
/*
* 文件名：CXFTestServlet.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CXFTestServlet.java
 * 修改人：chunfeng
 * 修改时间：2015年4月17日
 * 修改内容：新增
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.transport.servlet.CXFServlet;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper.BufferedRequestWrapper;
import com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper.BufferedResponseWrapper;
import com.better517na.JavaServiceRouteHelper.business.servlet.operateData.OperateHttpData;
import com.better517na.JavaServiceRouteHelper.business.servlet.operateData.OperateHttpDataImpFactory;
import com.better517na.JavaServiceRouteHelper.business.servlet.operateData.OperateImplEnum;
import com.better517na.logcompontent.model.MLogException;
import com.better517na.logcompontent.util.ExceptionLevel;

/**.
 * TODO 继承 CXFServlet 加一点拦截的功能
 * 
 * @author chunfeng
 */
public class CXFWraperServlet extends CXFServlet {

    /**.
     * 序列号
     */
    private static final long serialVersionUID = -6850230493228393757L;

    /**.
     * 重写父类中的invoke方法
     */
    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            //记日志以及attach的处理的实现
            OperateHttpData logs = OperateHttpDataImpFactory.getInstance().getOperateImp(OperateImplEnum.LOGANDATTACH);
            
            //包装request  response
            BufferedRequestWrapper wrapperRequest = new BufferedRequestWrapper(request);
            BufferedResponseWrapper wrapperResponse = new BufferedResponseWrapper(response);
            
            String requestParam = wrapperRequest.getInputContent(); 
            logs.beforeInvoke(requestParam, getRemortIP(request));
            //实际方法的调用
            super.invoke(wrapperRequest, wrapperResponse);
            //将数据写入response
            wrapperResponse.getOrginalArrayOutput().writeTo(response.getOutputStream());
            
            String responseResult = wrapperResponse.getResponseContent();
            
            logs.afterInvoke(requestParam, responseResult, request.getRemoteAddr());
        } catch (Exception e){
            MLogException mLogException = new MLogException(ExceptionLevel.Error, "v4150", "CXFWraperServlet 调用调用方法异常", e);
            mLogException.setUrl("客户端的ip:"+request.getRemoteAddr());
            LogOperate.writeExceptionLogSafe(mLogException);
        }
    }
    
    /**.
     * 
     * TODO 获取ip地址
     * 
     * @param request HttpServletRequest
     * @return ip
     */
    private String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip== null || "unKnown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
