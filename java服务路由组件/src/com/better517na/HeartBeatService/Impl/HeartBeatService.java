package com.better517na.HeartBeatService.Impl;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.better517na.HeartBeatService.IHeartBeatContract;

/**
 * . 服务路由心跳答应实现
 * 
 * @author chunfeng
 *
 */
@WebService(endpointInterface = "com.better517na.HeartBeatService.IHeartBeatContract")
public class HeartBeatService implements IHeartBeatContract {
    /**
     * {@inheritDoc}.
     */
    public javax.xml.datatype.XMLGregorianCalendar heartBeatCsharp() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Date heartBeatJava() {
        return new Date();
    }
}
