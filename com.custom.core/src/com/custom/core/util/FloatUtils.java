package com.custom.core.util;

/***
 * 浮点数工具类
 * @Company SINA
 *
 * @Copyright 2015-2016
 *
 * @author junfeng
 *
 * @date 2015-11-9 下午3:40:40
 *
 * @Version 3.0
 */
public class FloatUtils {
    /** +0*/
    public static float EPSILON    = 0.0000001f;
    /** -0*/
    public static float EPSILONNGT = -0.0000001f;
    
    /**
     * 浮点零值判定
     * @param val
     * @return
     */
    public static boolean isZero(float val) {
        return (val > EPSILONNGT && val < EPSILON);
    }
}
