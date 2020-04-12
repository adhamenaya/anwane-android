package com.test.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class SharedPref {
    private static final String SHARED_PREF_NAME = "RandooPref";
    public static SharedPreferences mPref;
    public static SharedPreferences.Editor mEditor;
    private static Context mContext;

    public static final String RANDOU_ENABLED = "isRandouEnabled";
    public static final String USER_ID = "userId";
    public static final String IS_LOGGED = "isLogged";
    public static final String USERNAME = "userName";
    public static final String LOGIN_ID = "loginId";
    public static final String LOGIN_TYPE = "loginType";
    public static final String USER_IMAGE = "userImage";


    private SharedPref() {
    }

    public static void initPref(Context mContext) {
        mPref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        SharedPref.mContext = mContext;
    }

    public static SharedPreferences getInstance(Context mContext) {
        mPref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        return mPref;
    }

    public static SharedPreferences.Editor getEditor(Context mContext) {
        mPref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        return mEditor;
    }
    /*Put methods*/

    public static void putString(String key, String val) {
        mEditor.putString(key, val).commit();
    }

    public static void putInt(String key, int val) {
        mEditor.putInt(key, val).commit();
    }

    public static void putFloat(String key, float val) {
        mEditor.putFloat(key, val).commit();
    }

    public static void putLong(String key, long val) {
        mEditor.putLong(key, val).commit();
    }

    public static void putBoolean(String key, Boolean val) {
        mEditor.putBoolean(key, val).commit();
    }
    /*Get methods*/

    public static String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return mPref.getFloat(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public static void clearAll() {
        mEditor.clear();
        mEditor.commit();
    }

}
