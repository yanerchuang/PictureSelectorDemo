package com.ywj.pictureselectordemo.utils;

import android.util.Log;

public class Logger {

    public static final String TAG = "Logger";
    public static final boolean DEBUG = true;
    
    public static void d(String value){
        if(DEBUG) {
            Log.d(TAG, value);
        }
    }

    public static void e(String value) {
        if(DEBUG) {
            Log.e(TAG, value);
        }
    }

    public static void e(Exception value) {
        if(DEBUG && value != null) {
            Log.e(TAG, value.getMessage());
        }
    }

    public static void i(String value){
        if(DEBUG) {
            Log.i(TAG, value);
        }
    }

    public static void w(String value){
        if(DEBUG) {
            Log.w(TAG, value);
        }
    }
}