/*
 * 文件名：ClientOutInterceptor.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ClientOutInterceptor.java
 * 修改人：chunfeng
 * 修改时间：2015年4月14日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.cxfInterceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

/**.
 *  强制设定客户端采用post方式提交
 * @author chunfeng
 */
public class ClientSetPostSubmitInterceptor extends AbstractSoapInterceptor {

    /**
     * . 构造函数.
     */
    public ClientSetPostSubmitInterceptor() {
        // 在写入信息之前拦截
        super(Phase.WRITE);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        //获取请求方法，GET或POST，默认使用POST  
        message.put(Message.HTTP_REQUEST_METHOD, "POST");  
    }
}
