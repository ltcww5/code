/*
 * 文件名：ThriftContext.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThriftContext.java
 * 修改人：guokan
 * 修改时间：2016年4月27日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.thrift.invoke;

import java.util.Map;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author guokan
 */
public class ThriftContext {

    /**
     * @上下文线程变量
     */
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * @服务端/客户端公用上下文线程变量.这个变量类的数据一直在调用链中传递
     */
    public static ThreadLocal<Map<String, Object>> cascadeThreadLocal = new ThreadLocal<Map<String, Object>>();
}
