package com.better517na.HeartBeatService;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * . 心跳的接口
 * 
 * @author chunfeng
 *
 */
@WebService()
public interface IHeartBeatContract {
    /**
     * . 返回当前服务器时间
     * @return 时间
     */
    @WebResult(name = "HeartBeatResult", targetNamespace = "http://tempuri.org/")
    @Action(input = "http://tempuri.org/IHeartBeatContract/HeartBeat", output = "http://tempuri.org/IHeartBeatContract/HeartBeatResponse")
    @RequestWrapper(localName = "HeartBeat", targetNamespace = "http://tempuri.org/", className = "Better517na.Core.AsyncInfrastructure.IHeartBeatContract.HeartBeat")
    @WebMethod(operationName = "HeartBeat", action = "http://tempuri.org/IHeartBeatContract/HeartBeat")
    @ResponseWrapper(localName = "HeartBeatResponse", targetNamespace = "http://tempuri.org/", className = "Better517na.Core.AsyncInfrastructure.IHeartBeatContract.HeartBeatResponse")
    javax.xml.datatype.XMLGregorianCalendar heartBeatCsharp();
    
    /**. java的心跳
     * @return 心跳返回
     */
    Date heartBeatJava();
}
