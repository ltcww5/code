/*
 * 文件名：ClientOutInterceptor.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ClientOutInterceptor.java
 * 修改人：chunfeng
 * 修改时间：2015年4月14日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInterceptor;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.model.CxfTransferModel;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;

/**
 * . 服务端出口的interceptor(用于设置附加信息和trackId)
 * 
 * @author chunfeng
 */
public class ClientOutInterceptor extends AbstractSoapInterceptor {

    /**
     * . 构造函数.
     */
    public ClientOutInterceptor() {
        // 在写入信息之前拦截
        super(Phase.WRITE);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Map<String, String> attach = ServiceRouteAttachHelper.THREAD_ATTACH_IN.get();
        // 有值 则往消息头里面扔
        if (attach != null && attach.size() > 0) {
            List<Header> headers = message.getHeaders();

            Element el = generateTrackIdElement(attach);
            if (el != null){
                QName q1 = new QName(LogOperate.NAMESPACEURI, LogOperate.TRACKID_KEY);
                SoapHeader h1 = new SoapHeader(q1, el);
    
                headers.add(h1);
            } 
            
            //去除trackId相关东西
            attach.remove(LogOperate.TRACKID_VAL_KEY);
            attach.remove(LogOperate.TRACKID_STARTTIME_KEY);
            
            el = generateValElement(attach);
            if (el != null){
                // 加入头
                QName q = new QName(LogOperate.NAMESPACEURI, ServiceRouteAttachHelper.ATTACH_KEY_VAL);
                SoapHeader h = new SoapHeader(q, el);
                headers.add(h);
            }
           
        }
        // 清空
        ServiceRouteAttachHelper.THREAD_ATTACH_IN.set(null);
    }

    /**
     * .  生成基于dom树的结构
     * 
     * @param attach
     *            attach
     * @return 生成的element
     */
    private Element generateValElement(Map<String, String> attach) {
        if (attach == null || attach.size() == 0) {
            return null;
        }

        CxfTransferModel cxf = new CxfTransferModel(attach);
        Document document = DOMUtils.createDocument();
        Element cxfElement = document.createElement(ServiceRouteAttachHelper.ATTACH_ELEMENT_KEY_VAL);
        cxfElement.setTextContent(JsonUtil.toJson(cxf));

        return cxfElement;
    }
    
    /**
     * . 生成基于dom树的结构
     * 构造C#需要个格式  除了trackId和trackStartTime 其他全部写死
     * 
     * @param attach
     *            attach
     * @return 生成的element
     */
    private Element generateTrackIdElement(Map<String, String> attach) {
        if (attach == null || attach.size() == 0) {
            return null;
        }
        
        Document document = DOMUtils.createDocument();
        Element trackIdHead = document.createElementNS(LogOperate.NAMESPACEURI, LogOperate.TRACKID_KEY);
        trackIdHead.setAttribute("xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        String ss = "http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log";
        Element trackIdTrackId = document.createElementNS(ss, LogOperate.TRACKID_VAL_KEY);
        trackIdTrackId.setTextContent(attach.get(LogOperate.TRACKID_VAL_KEY));
        Element trackIdStarttime = document.createElementNS(ss, LogOperate.TRACKID_STARTTIME_KEY);
        trackIdStarttime.setTextContent(attach.get(LogOperate.TRACKID_STARTTIME_KEY));
        Element exceptionLevel = document.createElementNS(ss, "_x003C_Level_x003E_k__BackingField");
        exceptionLevel.setTextContent("Info");
        Element e2 = document.createElementNS(ss, "_x003C_OriginalTrackID_x003E_k__BackingField");
        e2.setAttribute("i:nil", "true");
        e2.setTextContent("");
        Element e3 = document.createElementNS(ss, "exceptionId");
        e3.setTextContent("00000000-0000-0000-0000-000000000000");
        Element e4 = document.createElementNS(ss, "innerSequence");
        e4.setTextContent("1");
        Element e5 = document.createElementNS(ss, "prefix");
        e5.setTextContent("0-");
        Element e6 = document.createElementNS(ss, "sequence");
        e6.setTextContent("1");
        trackIdHead.appendChild(exceptionLevel);
        trackIdHead.appendChild(e2);
        trackIdHead.appendChild(e3);
        trackIdHead.appendChild(e4);
        trackIdHead.appendChild(e5);
        trackIdHead.appendChild(e6);
        trackIdHead.appendChild(trackIdStarttime);
        trackIdHead.appendChild(trackIdTrackId);

        return trackIdHead;
    }
}
