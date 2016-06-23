package com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper;

/*
 * 文件名：BufferedRequestWrapper.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BufferedRequestWrapper.java
 * 修改人：chunfeng
 * 修改时间：2015年4月18日
 * 修改内容：新增
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * . 包装http请求的request
 * 
 * @author chunfeng
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {
    /**
     * . 缓冲区
     */
    private ByteArrayInputStream bais;

    /**
     * . 当前构造的输入流对象
     */
    private BufferedServletInputStream bsis;

    /**
     * . 缓冲区byte数组
     */
    private byte[] buffer;

    /**
     * .
     * 
     * 构造函数.
     * 
     * @param req
     *            req
     * @throws IOException
     *             IOException
     */
    public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        // 将request输入流中的数据读到当前对象的缓存区中
        InputStream is = req.getInputStream();

        // 缓冲区
        List<InnerByteModel> info = new ArrayList<InnerByteModel>();
        byte[] tempBuff = new byte[1024];
        int totalCount = 0;
        int readNum = 0;
        // 所有读取的内容都使用temp接收
        while ((readNum = is.read(tempBuff, 0, 1024)) != -1) {
            byte[] valueInfo = Arrays.copyOf(tempBuff, 1024);
            InnerByteModel innerInfo = new InnerByteModel();
            innerInfo.setByteInfo(valueInfo);
            innerInfo.setValueNum(readNum);
            info.add(innerInfo);
            totalCount += readNum;
        }
        buffer = new byte[totalCount];
        int index = 0;
        for (int i = 0, len = info.size(); i < len; i++) {
            InnerByteModel tm = info.get(i);
            byte[] innerByteInfo = tm.getByteInfo();
            int valueNum = tm.getValueNum();
            for (int j = 0, jlen = valueNum; j < jlen; j++) {
                buffer[index++] = innerByteInfo[j];
            }
        }
        // 释放
        info = null;
    }

    /**
     * .
     * 
     * @author chunfeng
     *
     */
    class InnerByteModel {
        /**
         * . 内容
         */
        private byte[] byteInfo;

        /**
         * . 数据量
         */
        private int valueNum;

        /**
         * 设置valueNum.
         * 
         * @return 返回valueNum
         */
        public int getValueNum() {
            return valueNum;
        }

        /**
         * 获取valueNum.
         * 
         * @param valueNum
         *            要设置的valueNum
         */
        public void setValueNum(int valueNum) {
            this.valueNum = valueNum;
        }

        /**
         * 设置byteInfo.
         * 
         * @return 返回byteInfo
         */
        public byte[] getByteInfo() {
            return byteInfo;
        }

        /**
         * 获取byteInfo.
         * 
         * @param byteInfo
         *            要设置的byteInfo
         */
        public void setByteInfo(byte[] byteInfo) {
            this.byteInfo = byteInfo;
        }
    }

    /**
     * . TODO 获取inputStream中的内容
     * 
     * @return 内容
     */
    public String getInputContent() {
        try {
            return new String(buffer, 0, buffer.length, "UTF-8").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * . 重写接口中的获取inputStream方法,用当前构造的inputStream返回
     */
    @Override
    public ServletInputStream getInputStream() {
        try {
            bais = new ByteArrayInputStream(buffer);
            bsis = new BufferedServletInputStream(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bsis;
    }

    /**
     * . {@inheritDoc}.
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        bais.close();
        bsis.close();
    }

}
