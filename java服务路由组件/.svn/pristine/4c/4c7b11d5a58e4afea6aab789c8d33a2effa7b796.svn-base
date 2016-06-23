package com.better517na.JavaServiceRouteHelper.business.cxfInvoke;

/*
 * 文件名：HandleBizParam.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HandleBizParam.java
 * 修改人：yishao
 * 修改时间：2015年3月31日
 * 修改内容：新增
 */

import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * 处理业务参数.
 * 
 * @author yishao
 */
public class HandleBizParam {

    /**
     * 获取method. 区别方法重载 采用默认的参数类型
     * 
     * @param objects
     *            可变长参数
     * @param <T>
     *            create
     * @param t
     *            create
     * @param methodName
     *            方法名
     * @return method
     * @throws Exception
     *             Exception
     */
    public static <T> Method getMethod(Class<T> t, String methodName, Object[] objects) throws Exception {
        if (t == null || StringUtil.isNullOrEmpty(methodName)) {
            throw new InvalidParameterException("反射获取接口方法,入参不能为空!!!");
        }
        if (objects == null) {
            objects = new Object[0];
        }
        // 获取所有的方法定义
        Method[] methodes = t.getMethods();
        String tempMethodName = methodName.intern();
        // 用于模糊匹配的操作
        List<Method> mlists = new ArrayList<Method>();
        for (int i = 0, len = methodes.length; i < len; i++) {
            Method mt = methodes[i];
            // 第一步 过滤方法名
            if (!tempMethodName.equals(mt.getName())) {
                continue;
            }
            // 过滤参数个数
            if (mt.getParameterTypes().length != objects.length) {
                continue;
            }

            // 留着用于模糊匹配使用
            mlists.add(mt);

            // 严格过滤参数类型
            if (!checkPaPypeStrict(mt.getParameterTypes(), objects)) {
                continue;
            }
            // 这几步都过了 就能直接返回了
            // 释放资源
            mlists = null;
            return mt;
        }

        // 进行模糊匹配
        for (int i = 0, len = mlists.size(); i < len; i++) {
            Method mt = mlists.get(i);

            // 依次比较每一个参数类型
            boolean ckeckOk = true;
            Class<?>[] ptypes = mt.getParameterTypes();
            for (int j = 0, tlen = ptypes.length; j < tlen; j++) {
                Object tpo = objects[j];
                // 传null的 不管
                if (tpo == null) {
                    continue;
                }
                // 判断参数类型是否是其子类
                if (!ptypes[j].isAssignableFrom(tpo.getClass())) {
                    // 判断是否是属于包装类型的匹配
                    if (checkWithBoxType(ptypes[j], tpo.getClass())) {
                        continue;
                    }
                    ckeckOk = false;
                    break;
                }
            }
            // 没检查通过
            if (!ckeckOk) {
                continue;
            }
            return mt;
        }
        throw new NoSuchMethodException(t.getName() + " 中没有找到方法" + methodName + ",参数类型:[" + getObjectsDes(objects) + "]");
    }

    /**
     * . 获取参数的描述符
     * 
     * @param objects
     *            objects
     * @return 描述字符串
     */
    private static String getObjectsDes(Object[] objects) {
        if (objects.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(objects[i] == null ? "null" : objects.getClass().getName());
        }
        return sb.toString();
    }

    /**
     * . 比较参数类型是否一样 严格比较.
     * 
     * @param parameter1
     *            parameter1
     * @param parameter2
     *            parameter2
     * @return 结果
     */
    private static boolean checkPaPypeStrict(Class<?>[] parameter1, Object[] parameter2) {
        for (int i = 0; i < parameter1.length; i++) {
            Object tpo = parameter2[i];
            // 传null的 不管
            if (tpo == null) {
                continue;
            }
            Class<?> requestClass = tpo.getClass();
            if (parameter1[i] != requestClass) {
                if (!checkWithBoxType(parameter1[i], requestClass)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * . 对于可以采用包装类型的转换成原始类型进行比较.
     * 
     * @param c1
     *            转变的class
     * @param c2
     *            对比的class
     * @return 是否相等
     */
    private static boolean checkWithBoxType(Class<?> c1, Class<?> c2) {
        Class<?> tc1 = getBoxClass(c1);
        boolean rboolean = tc1 == c2;
        if (rboolean) {
            return true;
        }
        // 针对 int long byte short 处理成同一种类型
        if (tc1 == Integer.class || tc1 == Long.class || tc1 == Byte.class || tc1 == Short.class) {
            Class<?> tc2 = getBoxClass(c2);
            if (tc2 == Integer.class || tc2 == Long.class || tc2 == Byte.class || tc2 == Short.class) {
                return true;
            }
        }
        return false;
    }

    /**
     * . 获取class的包装类型
     * 
     * @param c
     *            入参
     * @return 结果
     */
    private static Class<?> getBoxClass(Class<?> c) {
        if (c == int.class) {
            c = Integer.class;
        } else if (c == boolean.class) {
            c = Boolean.class;
        } else if (c == long.class) {
            c = Long.class;
        } else if (c == float.class) {
            c = Float.class;
        } else if (c == double.class) {
            c = Double.class;
        } else if (c == char.class) {
            c = Character.class;
        } else if (c == byte.class) {
            c = Byte.class;
        } else if (c == short.class) {
            c = Short.class;
        }
        return c;
    }

    /**
     * 执行方法.
     * 
     * @param t
     *            create
     * @param <T>
     *            create
     * @param objects
     *            可变长参数
     * @param method
     *            method
     * @return 结果
     * @throws Exception
     *             Exception
     */
    public static <T> Object methodInvoke(T t, Method method, Object[] objects) throws Exception {
        // 默认值为null
        Object result = null;
        switch (objects.length) {
            case 0:
                result = method.invoke(t);
                break;
            case 1:
                result = method.invoke(t, objects[0]);
                break;
            case 2:
                result = method.invoke(t, objects[0], objects[1]);
                break;
            case 3:
                result = method.invoke(t, objects[0], objects[1], objects[2]);
                break;
            case 4:
                result = method.invoke(t, objects[0], objects[1], objects[2], objects[3]);
                break;
            case 5:
                result = method.invoke(t, objects[0], objects[1], objects[2], objects[3], objects[4]);
                break;
            default:
                break;
        }
        return result;
    }
}
