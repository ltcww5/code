/*
 * 文件名：DateUtil.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： DateUtil.java
 * 修改人：chunfeng
 * 修改时间：2015年4月10日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.util;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * . 日期处理函数
 * 
 * @author chunfeng
 */
public class DateUtil {

    /**
     * . 默认时间字符串格式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * . C#时间字符串格式
     */
    public static final String CSHARP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    
    /**
     * @判断某个时间跟当前时间比较 是否已经超期minitues分钟
     * 
     * @param src 要比较的时间
     * @param minitues 超期的分钟数
     * @return 是否已经超期
     */
    public static boolean judgeTimeWithNow(Date src, int minitues) {
        if (src == null) {
            throw new InvalidParameterException("时间比较的入参时间不能为空");
        }
        Calendar now = Calendar.getInstance();

        Calendar srcc = Calendar.getInstance();
        srcc.setTime(src);
        srcc.add(Calendar.MINUTE, minitues);

        return now.after(srcc);
    }
    
    /**
     * @判断某个时间跟当前时间比较 是否已经超期seconds秒
     * 
     * @param src 要比较的时间
     * @param seconds
     *            超期的秒数
     * @return 是否已经超期
     */
    public static boolean judgeTimeSecondWithNow(Date src, int seconds) {
        if (src == null) {
            throw new InvalidParameterException("时间比较的入参时间不能为空");
        }
        Calendar now = Calendar.getInstance();

        Calendar srcc = Calendar.getInstance();
        srcc.setTime(src);
        srcc.add(Calendar.SECOND, seconds);

        return now.after(srcc);
    }
    
    /**
     * @格式:yyyy-MM-ddTHH:mm:ss
     * 
     * @param times 时间戳
     * @return 字符串
     */
    public static String getStardDateStr(long  times){
        Date t = new Date(times);
        SimpleDateFormat formatter = new SimpleDateFormat(CSHARP_PATTERN);
        return formatter.format(t);
    }
    
    /**
     * @格式:yyyy-MM-ddTHH:mm:ss
     * 
     * @param times 时间格式字符串
     * @return long值
     */
    public static long getStardDateLong(String times){
        long defaultTimes = new Date().getTime();
        
        if (StringUtil.isNullOrEmpty(times)){
            return defaultTimes;
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat(CSHARP_PATTERN);
        Date valTime = null;
        try {
            valTime = formatter.parse(times.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return defaultTimes;
        }
        return valTime.getTime();
    }
    
    /**
     * @计算给出时间与当前时间的时间差(单位秒)
     * 
     * @param diffTime 给出的时间
     * @return 时间差
     */
    public static String getNowWitTimeGap(long diffTime){
        long l1 = new Date().getTime();
        long milles = l1 - diffTime;
        String tempStr = String.format("%07d", milles);
        return tempStr+".000";
    }

    /**
     * 描述：日期转换格式.
     * 
     * @param date 时间
     * @param pattern 格式
     * @return string 返回时间
     * @version v1.0
     * @date 2013-3-20 下午04:27:21
     */
    public static String dateToString(Date date, String pattern) {
        String temp = "";
        if (pattern != null && !"".equals(pattern)) {
            temp = pattern;
        } else {
            temp = DEFAULT_PATTERN;
        }
        SimpleDateFormat df = new SimpleDateFormat(temp);
        try {
            String str = df.format(date);
            return str;
        } catch (Exception e) {
            throw new RuntimeException("时间格式转换异常" + e.getMessage());
        }
    }
}
