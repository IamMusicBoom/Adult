package com.wma.adult;

public class Log {
    private final static String TAG = "WMA-WMA: ";
    public static void d(String tag,String msg){
        System.out.println(TAG + tag + ":" + msg);
    }
}
