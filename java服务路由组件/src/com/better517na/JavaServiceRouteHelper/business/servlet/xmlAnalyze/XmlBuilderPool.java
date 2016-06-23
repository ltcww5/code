/*
 * 文件名：XmlBuilderPool.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： XmlBuilderPool.java
 * 修改人：chunfeng
 * 修改时间：2015年4月20日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.xmlAnalyze;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**.
 * TODO xml 解析器线程池
 * 因为创建一个解析器比较耗时  所以用一个池子来管理
 * @author     chunfeng
 */
public final class XmlBuilderPool {
    /**.
     * 线程安全的池子
     */
    private ConcurrentLinkedQueue<DocumentBuilder> builderPool = new ConcurrentLinkedQueue<DocumentBuilder>();
    
    /**.
     * 单例的唯一对象
     */
    private static XmlBuilderPool instance;
    
    /**.
     * 
     * 构造函数.
     *
     */
    private XmlBuilderPool(){
    }
    
    /**.
     * 
     * TODO 单例返回
     * 
     * @return 返回xmlbuilder线程池对象
     */
    public static XmlBuilderPool getInstance(){
        if (instance == null){
            synchronized (XmlBuilderPool.class) {
                if (instance == null){
                    instance = new  XmlBuilderPool();
                }
            }
        }
        return instance;
    }
    
    /**.
     * 
     * TODO 从线程池里面获取xml解析器对象
     * @return 返回一个xml解析器
     * @throws ParserConfigurationException 
     */
    public DocumentBuilder getXmlBuilder() throws ParserConfigurationException{
        DocumentBuilder val = builderPool.poll();
        if (val == null){
            //解析器
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
            val = factory.newDocumentBuilder();
            return val;
        }
        return val;
    }
    
    /**.
     * 
     * TODO 退还
     * 
     * @param builder 退还的解析器
     */
    public void returnXmlBuilder(DocumentBuilder builder){
        builderPool.add(builder);
    }
}
