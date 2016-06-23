/*
 * 文件名：ClientOutInterceptor.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ClientOutInterceptor.java
 * 修改人：chunfeng
 * 修改时间：2015年4月14日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInterceptor;  

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import com.better517na.JavaServiceRouteHelper.model.CxfTransferModel;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;

/**
 * .  服务器端的调用入口的interceptor
 * 
 * @author chunfeng
 */
public class ServerInInterceptor extends AbstractSoapInterceptor {

    /**
     * . 指定拦截器使用的阶段 这里是服务端对header的拦截处理，指定到UNMARSHAL阶段
     * 
     * 注意：不要指定到READ阶段， READ阶段获取到的是xml数据，还不能从message对象中获取到header的相关信息
     * 
     * 在unmarshaller将xml转换 为对象之后使用拦截器，才能从header对象中获取到数据
     */
    public ServerInInterceptor() {
        super(Phase.UNMARSHAL);
    }

    /**
     * {@inheritDoc}.
     */
    public void handleMessage(SoapMessage message) throws Fault {
        // 默认值
        Map<String, String> hmap = new HashMap<String, String>();

        Header h = message.getHeader(new QName(ServiceRouteAttachHelper.ATTACH_KEY_VAL));

        if (h != null) {
            hmap = analyzDomElement((Element) h.getObject());
        }

        // 不管有没有值 都覆盖
        ServiceRouteAttachHelper.THREAD_ATTACH_OUT.set(hmap);
    }

    /**
     * .
     *   解析生成的获取的element 成map
     * 
     * @param el
     *            el
     * @return 附加值的map
     */
    private Map<String, String> analyzDomElement(Element el) {
        String contentVal = el.getTextContent();

        CxfTransferModel cxf = JsonUtil.toObject(contentVal, CxfTransferModel.class);

        return cxf.getAttach();
    }
}
