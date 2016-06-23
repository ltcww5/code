package com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper;

/*
* 文件名：BufferedServeletInputStream.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BufferedServeletInputStream.java
 * 修改人：chunfeng
 * 修改时间：2015年4月18日
 * 修改内容：新增
 */

import java.io.ByteArrayInputStream;

import javax.servlet.ServletInputStream;

/**.
 * TODO ServletInputStream 的实现
 * 
 * @author chunfeng
 */
public class BufferedServletInputStream extends ServletInputStream {
    
    /**.
     * 缓冲区流
     */
    private ByteArrayInputStream bais;

    /**.
     * 
     * 构造函数.
     * 
     * @param bais bais
     */
    public BufferedServletInputStream(ByteArrayInputStream bais) {
        this.bais = bais;
    }

    /**.
     * 
     * {@inheritDoc}.
     */
    @Override
    public int available() {
        return bais.available();
    }

    /**.
     * 
     * {@inheritDoc}.
     */
    @Override
    public int read() {
        return bais.read();
    }

    /**.
     * 
     * {@inheritDoc}.
     */
    @Override
    public int read(byte[] buf, int off, int len) {
        return bais.read(buf, off, len);
    }

}
