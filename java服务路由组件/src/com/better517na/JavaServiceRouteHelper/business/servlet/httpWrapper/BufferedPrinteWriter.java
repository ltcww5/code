/*
 * 文件名：BufferedPrinteWriter.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BufferedPrinteWriter.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * . TODO 包装的printWriter
 * 
 * @author chunfeng
 */
public class BufferedPrinteWriter extends PrintWriter {

    /**
     * . 此即为存放response输入流的对象
     */
    private ByteArrayOutputStream myOutput;

    /**
     * . 构造函数.
     * 
     * @param output
     *            output
     */
    public BufferedPrinteWriter(ByteArrayOutputStream output) {
        super(output);
        this.myOutput = output;
    }

    /**
     * .
     * 
     * TODO 输出流字节数组
     * 
     * @return 结果
     */
    public ByteArrayOutputStream getByteArrayOutputStream() {
        return myOutput;
    }
}
