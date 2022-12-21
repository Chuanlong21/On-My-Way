package com.chuan.on_my_way.utility;


//基于ThreadLocal封装工具类，用户用于保存和获取当前登录的用户id
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrent(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrent(){
        return threadLocal.get();
    }

}
