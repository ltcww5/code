/*
 * 文件名：CxfTransferModel.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CxfTransferModel.java
 * 修改人：chunfeng
 * 修改时间：2015年4月14日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * . TODO cxf 转化的mode
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author chunfeng
 */
public class CxfTransferModel implements Serializable {

    /**
     * 添加字段注释.
     */
    private static final long serialVersionUID = 1L;

    /**
     * . 传的map值
     */
    private Map<String, String> attach;

    /**
     * . 处理时间
     */
    private Date generateTime;

    /**
     * .
     * 
     * 构造函数.
     * 
     * @param attach
     *            attach
     */
    public CxfTransferModel(Map<String, String> attach) {
        this.attach = attach;
        this.generateTime = new Date();
    }

    /**
     * .
     * 
     * 得到attach
     * 
     * @return attach
     */
    public Map<String, String> getAttach() {
        return attach;
    }

    /**
     * . 设置attach
     * 
     * @param attach
     *            attach
     */
    public void setAttach(Map<String, String> attach) {
        this.attach = attach;
    }

    /**
     * . 获取 generateTime
     * 
     * @return generateTime
     */
    public Date getGenerateTime() {
        return generateTime;
    }
}
