/*
 * 文件名：TestHandler.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TestHandler.java
 * 修改人：guokan
 * 修改时间：2016年4月25日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.thrift.invoke;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.better517na.JavaServiceRouteHelper.event.Handler;
import com.better517na.JavaServiceRouteHelper.thrift.log.ThriftLogAttach;

/**
 * @thrift服务器端回调。隐式提交参数
 * @author guokan
 */
public class ThriftServiceHandler implements TServerEventHandler {

    /**
     * @日志记录对象
     */
    private static ThriftLogAttach logAttach;

    /**
     * 添加字段注释.
     */
    private Handler handler;

    /**
     * 构造函数.
     * 
     * @param handler
     *            handler
     */
    public ThriftServiceHandler(Handler handler) {
        super();
        this.handler = handler;
    }

    /**
     * 构造函数.
     * 
     */
    public ThriftServiceHandler() {
        super();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void preServe() {
        // System.out.println("preServe..");
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ServerContext createContext(TProtocol input, TProtocol output) {
        // System.out.println("createContext..");
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteContext(ServerContext paramServerContext, TProtocol input, TProtocol output) {
        // System.out.println("deleteContext..");
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void processContext(ServerContext paramServerContext, TTransport in, TTransport out) {
        try {
            TSocket socket = (TSocket) in;
            String clientIP = socket.getSocket().getInetAddress().getHostAddress();
            // 读取前4位，定义了object的大小
            byte[] b = new byte[4];
            socket.read(b, 0, 4);
            int len = byte2int(b);
            byte[] objArr = new byte[len];
            socket.read(objArr, 0, len);
            Map<String, Object> map = arrToObject(objArr);
            for (String key : map.keySet()) {
                ThriftUtils.set(key, map.get(key), false);
            }
            socket.read(b, 0, 4);
            len = byte2int(b);
            objArr = new byte[len];
            socket.read(objArr, 0, len);
            map = arrToObject(objArr);
            for (String key : map.keySet()) {
                ThriftUtils.set(key, map.get(key), true);
            }
            ThriftUtils.set("Thrift.clientIP", clientIP, true);
            logAttach.getInstance().beforeInvoke(map, clientIP); // 设置trackid到线程中
            // guokan 2016-04-26
            if (null != handler) {
                handler.receive();
            }
        } catch (Exception e) {
            System.out.println();
        }

    }

    /**
     * @int2byte
     * @param res
     *            res
     * @return byte[]
     */
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff); // 最低位
        targets[1] = (byte) ((res >> 8) & 0xff); // 次低位
        targets[2] = (byte) ((res >> 16) & 0xff); // 次高位
        targets[3] = (byte) (res >>> 24); // 最高位,无符号右移。
        return targets;
    }

    /**
     * @byte2int
     * @param res
     *            res
     * @return int
     */
    public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    /**
     * 对象转数组.
     * 
     * @param obj
     *            obj
     * @return byte[]
     * @throws Exception
     *             Exception
     */
    public static byte[] objToArr(Map<String, Object> obj) throws Exception {
        if (null == obj) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);

        byte[] bytes = bo.toByteArray();

        bo.close();
        oo.close();
        return bytes;
    }

    /**
     * @arrToObject
     * @param b
     *            b
     * @return Map
     * @throws Exception
     *             Exception
     */
    private static Map<String, Object> arrToObject(byte[] b) throws Exception {
        if (null == b) {
            return null;
        }
        ByteArrayInputStream bo = new ByteArrayInputStream(b);
        ObjectInputStream oo = new ObjectInputStream(bo);
        Map<String, Object> obj = (Map<String, Object>) oo.readObject();
        bo.close();
        oo.close();
        return obj;
    }

    /**
     * 设置handler. $getHandlerThriftServiceHandler${field}Handler
     * 
     * @return 返回handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * 获取handler.
     * 
     * @param handler
     *            要设置的handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}
