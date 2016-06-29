package com.kuilei.zhuyi.utils;

import android.util.Log;

/**
 * Created by lenovog on 2016/6/29.
 */
public class Logger {
    /**
     * 是否开启debug
     */
    public static boolean isDebug=true;


    /**
     * 错误
     * @param clazz
     * @param msg
     */
    public static void e(Class<?> clazz,String msg){
        if(isDebug){
            Log.e(clazz.getSimpleName(), msg+"");
        }
    }
    /**
     * 信息
     * @param clazz
     * @param msg
     */
    public static void i(Class<?> clazz,String msg){
        if(isDebug){
            Log.i(clazz.getSimpleName(), msg+"");
        }
    }
    /**
     * 警告
     * @param clazz
     * @param msg
     */
    public static void w(Class<?> clazz,String msg){
        if(isDebug){
            Log.w(clazz.getSimpleName(), msg+"");
        }
    }
}
