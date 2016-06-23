/*
 * 文件名：BufferedPrintWriter.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BufferedPrintWriter.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * . TODO response的printerWriter的包装类
 * 
 * @author chunfeng
 */
public class BufferedOutputStream extends ServletOutputStream {
    /**
     * . 此即为存放response输入流的对象
     */
    private ByteArrayOutputStream myOutput;

    /**
     * .
     * 
     * TODO 构造方法
     * 
     * @param output
     *            输入流的对象
     */
    public BufferedOutputStream(ByteArrayOutputStream output) {
        myOutput = output;
    }

    /**
     * . TODO 得到缓存区内存字节数组
     * 
     * @return 结果
     */
    public ByteArrayOutputStream getByteArrayOutputStream() {
        return myOutput;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void write(int content) throws IOException {
        myOutput.write(content);
    }
}
