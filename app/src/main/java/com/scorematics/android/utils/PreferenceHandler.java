package com.scorematics.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Common PrefrenceConnector class for storing preference values.
 * 
 * @author smadan
 *
 */
public class PreferenceHandler {
	public static final String PREF_NAME = "PEOPLE_PREFERENCES";
	public static final int MODE = Context.MODE_PRIVATE;
	
	public static final String USERID = "USERID";
	public static final String TOKEN = "TOKEN";
	public static final String IS_LOGIN = "IS_LOGIN";
	public static final String USER_ID = "USER_ID";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_EMAIL = "USER_EMAIL";
	public static final String PASSWORD = "PASSWORD";
	public static String SECONDRY_COLOR = "SECONDRY_COLOR";
	public static String PRIMARY_COLOR = "PRIMARY_COLOR";
	public static String UID ="UID";
	public static String KEY_FEATURE = "key_feature";
	public static String CUSTOMER_ID ="key_customer_id";
	public static String CARD_ID = "CartID";
	public static String KEY_CUSTOMER_FAETURE = "customer_feature";
	public static String Cart_COUNT = "cart_count";
	public static String COMMENT_ID ="key_comment_id";
	public static String KEY_CUT_OFF_TIME = "cut_off_time";
	public static String KEY_SAVE_CURRENT_TIME = "KEY_SAVE_CURRENT_TIME";
	public static String KEY_DELIVERY_DATE = "KEY_DELIVERY_DATE";


	public static void writeBoolean(Context context, String key, boolean value) {
		getEditor(context).putBoolean(key, value).commit();
		
	}

	public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();
	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();
	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	public static void writeFloat(Context context, String key, float value) {
		getEditor(context).putFloat(key, value).commit();
	}

	public static float readFloat(Context context, String key, float defValue) {
		return getPreferences(context).getFloat(key, defValue);
	}

	public static void writeLong(Context context, String key, long value) {
		getEditor(context).putLong(key, value).commit();
	}

	public static long readLong(Context context, String key, long defValue) {
		return getPreferences(context).getLong(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

	public static void removeKey(Context context, String key){
		getEditor(context).remove(key).commit();
	}



}
