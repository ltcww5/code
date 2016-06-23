/*
 * 文件名：ListConvertor.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ListConvertor.java
 * 修改人：chunfeng
 * 修改时间：2015年10月20日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.JavaTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * 兼容cxf的list.
 * 
 * @author chunfeng
 */
@XmlType(name = "ListListConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListListConvertor {
    /**
     * . 装数据的结构
     */
    private List<ListConvertor> listValue = new ArrayList<ListConvertor>();

    /**
     * . 加入数据
     * 
     * @param values
     *            values
     */
    public void addValue(List<Object> values) {
        ListConvertor listcon = new ListConvertor(values);
        listValue.add(listcon);
    }

    /**
     * . 返回数据
     * 
     * @return 数据
     */
    public List<List<Object>> getValue() {
        List<List<Object>> results = new ArrayList<List<Object>>();
        for (ListConvertor listcon : listValue) {
            results.add(listcon.getValues());
        }
        return results;
    }
}

/**
 * . list的转换对象
 * 
 * @author chunfeng
 */
class ListConvertor {
    /**
     * . 实际内容
     */
    private List<Object> values;

    /**
     * . 构造函数.
     */
    public ListConvertor() {
        this.values = new ArrayList<Object>();
    }

    /**
     * . 构造函数.
     * 
     * @param values
     *            values
     */
    public ListConvertor(List<Object> values) {
        if (values == null) {
            this.values = new ArrayList<Object>();
        } else {
            this.values = values;
        }
    }

    /**
     * 设置values.
     * 
     * @return 返回values
     */
    public List<Object> getValues() {
        return values;
    }

    /**
     * 获取values.
     * 
     * @param values
     *            要设置的values
     */
    public void setValues(List<Object> values) {
        this.values = values;
    }
}
