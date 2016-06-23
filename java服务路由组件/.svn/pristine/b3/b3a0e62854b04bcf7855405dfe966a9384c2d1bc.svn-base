package com.better517na.JavaServiceRouteHelper.business.servlet.httpWrapper;

/*
 * 文件名：BufferedResponseWrapper.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BufferedResponseWrapper.java
 * 修改人：chunfeng
 * 修改时间：2015年4月19日
 * 修改内容：新增
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**.
 * TODO 对response的输出流进行装饰
 * 支持outputStream 和 printeWriter的封装替换
 * @author     chunfeng
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper {
    /**.
     * 封装的printeWriter对象
     */
    private PrintWriter tprintWriter;
    
    /**.
     * outputStream对象
     */
    private BufferedOutputStream outputStream;  
    
    /**.
     * 缓冲区
     */
    private ByteArrayOutputStream output;  
  
    /**.
     * 
     * 构造函数.
     * 
     * @param httpServletResponse 待封装的response对象
     * @throws Exception 
     */
    public BufferedResponseWrapper(ServletResponse httpServletResponse) throws Exception {  
        super((HttpServletResponse)httpServletResponse);  
        output = new ByteArrayOutputStream();  
        outputStream = new BufferedOutputStream(output);  
        tprintWriter = new PrintWriter(new OutputStreamWriter(outputStream, this.getCharacterEncoding()));
    }  
  
    /**.
     * TODO response 流中的数据 转化为UTF-8的
     * @return 数据
     */
    public String getResponseContent() {  
        try {  
            tprintWriter.flush();
            //刷新该流的缓冲，详看java.io.Writer.flush()     
            String s = new String(output.toByteArray(), "UTF-8");  
            return s;  
        } catch (IOException e) {  
            return "UnsupportedEncoding";  
        }  
    }  
    
    /**.
     * TODO response 返回输出流
     * @return 数据
     * @throws IOException 
     */
    public ByteArrayOutputStream getOrginalArrayOutput() throws IOException {  
        tprintWriter.flush();
        //刷新该流的缓冲，详看java.io.Writer.flush()     
        return output;
    }  
  
    /**.
     * 重写getOutputStream 方法  ()
     * {@inheritDoc}.
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException{
        return this.outputStream;
    }
    
    /**.
     * 重写
     * {@inheritDoc}.
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        return this.tprintWriter;
    }
    
    /**.
     * TODO 重写
     * @throws IOException IOException
     */
    public void close() throws IOException {
        outputStream.close();  
        tprintWriter.close();
    }  
    
    /**.
     * {@inheritDoc}.
     */
    @Override
    public void finalize() throws Throwable {  
        super.finalize();  
        output.close();  
        outputStream.close();  
        tprintWriter.close();
    }  
}
