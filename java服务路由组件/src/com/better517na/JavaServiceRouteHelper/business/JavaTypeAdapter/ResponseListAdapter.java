package com.better517na.JavaServiceRouteHelper.business.JavaTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.better517na.commons.Response;

/**
 * . 公司内部使用Response<List<T>> 的泛型的转换
 * 
 * @author chunfeng
 */
@XmlType(name = "ResponseListConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseListAdapter extends XmlAdapter<ResponseListAdapter, Response<List<Object>>> {
    /**
     * . 实际存储数据的对象
     */
    private List<Object> values = new ArrayList<Object>();
    
    /**.
     * 返回码
     */
    private int code;
    
    /**.
     * 额外消息
     */
    private String message;
    
    /**.
     * 时间戳
     */
    private long time;

    /**
     * {@inheritDoc}.
     */
    @Override
    public ResponseListAdapter marshal(Response<List<Object>> inputValue) throws Exception {
        ResponseListAdapter responseAdapter = new ResponseListAdapter();
        responseAdapter.setValues(inputValue.getContent());
        responseAdapter.setCode(inputValue.getCode());
        responseAdapter.setMessage(inputValue.getMessage());
        responseAdapter.setTime(inputValue.getTime());
        return responseAdapter;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Response<List<Object>> unmarshal(ResponseListAdapter outputValue) throws Exception {
        Response<List<Object>> response = new Response<List<Object>>();
        response.setContent(outputValue.getValues());
        response.setCode(outputValue.getCode());
        response.setMessage(outputValue.getMessage());
        response.setTime(outputValue.getTime());
        return response;
    }

    /**
     * 设置code.
     * 
     * @return 返回code
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取code.
     * 
     * @param code 要设置的code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 设置message.
     * 
     * @return 返回message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取message.
     * 
     * @param message 要设置的message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置time.
     * 
     * @return 返回time
     */
    public long getTime() {
        return time;
    }

    /**
     * 获取time.
     * 
     * @param time 要设置的time
     */
    public void setTime(long time) {
        this.time = time;
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
