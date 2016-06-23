package com.better517na.JavaServiceRouteHelper.thrift.heartBeat.impl;

import java.util.Date;

import org.apache.thrift.TException;

import com.better517na.JavaServiceRouteHelper.thrift.heartBeat.ThriftHeartBeatService.Iface;
import com.better517na.JavaServiceRouteHelper.util.DateUtil;

/**
 * @thrift心跳接口
 * @author guangyin
 *
 */
public class ThriftHeartBeatServiceImpl implements Iface {
    @Override
    public String heartBeat() throws TException {
        Date d = new Date();
        return DateUtil.dateToString(d, null);
    }

}
