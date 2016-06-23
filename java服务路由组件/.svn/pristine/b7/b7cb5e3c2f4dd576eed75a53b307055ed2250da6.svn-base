/*
 * 文件名：RandomMap.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： RandomMap.java
 * 修改人：yishao
 * 修改时间：2015年3月27日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.util;

import java.util.Map;
import java.util.Random;

import com.better517na.JavaServiceRouteHelper.model.ServiceRouteInfoCache;

/**
 * 随机获取map中的元素.
 * 
 * @author yishao
 */
public class RandomMap {
    /**
     * 获取keySet.
     * 
     * @param map
     *            map
     * @return keySetStrings
     */
    public static String[] getKeySet(Map<String, ServiceRouteInfoCache> map) {
        // map的元素个数
        int mapSize = map.size();

        String[] keySetStrings = new String[mapSize];

        int[] sequence = getSequence(mapSize);

        int i = 0;
        for (String string : map.keySet()) {
            keySetStrings[sequence[i]] = string;
            i++;
        }

        return keySetStrings;
    }

    /**
     * @param no
     *            给定数目
     * @return 乱序后的数组
     */
    public static int[] getSequence(int no) {
        int[] sequence = new int[no];
        for (int i = 0; i < no; i++) {
            sequence[i] = i;
        }
        Random random = new Random();
        for (int i = 0; i < no; i++) {
            int p = random.nextInt(no);
            int tmp = sequence[i];
            sequence[i] = sequence[p];
            sequence[p] = tmp;
        }
        random = null;
        return sequence;
    }

}
