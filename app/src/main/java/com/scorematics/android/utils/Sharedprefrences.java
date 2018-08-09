package com.scorematics.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prerna.kapoor on 9/17/2015.
 */
public class Sharedprefrences {

    private static Sharedprefrences instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Object object;


    public void setObject(Context context, Object object, String prefKey) {
        Utilities.getInstance(context).writeObjectOnSharedPreference("",object, prefKey);
    }


    public Object getObject(Context context, String prefKey) throws Exception {
        if (pref.contains(prefKey)) {
            return Utilities.getInstance(context).readObjectFromSharedPreference("",prefKey);
        } else {
            return null;
        }

    }

    /**
     * @param context Parameterized Constructor is called
     */
    public static Sharedprefrences getInstance(Context context,
                                               String sharedPreferenceName) {
        return instance == null ? instance = new Sharedprefrences(context,
                sharedPreferenceName) : instance;
    }

    /**
     * Default Constructor To make this class work as SingleTon
     */
    private Sharedprefrences() {

    }

    /**
     * Constructor is called
     */
    private Sharedprefrences(Context context, String sharedPreferenceName) {
        pref = context.getSharedPreferences(sharedPreferenceName, 0);
        editor = pref.edit();
    }

    /**
     * Here all values is to put into shared prefrences
     *
     * @param key   Unique Key for a value
     * @param value Value to be stored
     */
    public void putboolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * Here all values is to get String value from shared preferences
     *
     * @param key          retrieve by unique key
     * @param defaultvalue give here defaultValue if not found defalutValue is assigned
     * @return string
     */
    public String getString(String key, String defaultvalue) {
        return pref.getString(key, defaultvalue);
    }

    /**
     * Here all values is to get int value from shared preferences
     *
     * @param key      retrieve by unique key
     * @param defValue give here defaultValue if not found defalutValue is assigned
     * @return int
     */
    public int getInt(String key, int defValue) {
        return pref.getInt(key, defValue);
    }

    /**
     * Here all values is to get boolean value from shared preferences
     *
     * @param key      retrieve by unique key
     * @param defValue give here defaultValue if not found defalutValue is assigned
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }

    /**
     * Here all values is to get long valued from shared preferences
     *
     * @param key      retrieve by unique key
     * @param defValue give here defaultValue if not found defalutValue is assigned
     * @return long
     */
    public long getLong(String key, long defValue) {
        return pref.getLong(key, defValue);
    }

    /**
     * Here all values is to get float value from shared preferences
     *
     * @param key      retrieve by unique key
     * @param defValue give here defaultValue if not found defalutValue is assigned
     * @return float
     */
    public float getFloat(String key, float defValue) {
        return pref.getFloat(key, defValue);
    }
}
