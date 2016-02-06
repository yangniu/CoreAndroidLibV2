package com.custom.core.util.android;


import android.content.Context;
import android.content.SharedPreferences;

import com.custom.core.R;

public class SharedPreferenceData {

	public static SharedPreferences getSharedPreferenceWithKey(Context context, int strId) {
		String name = context.getResources().getString(strId);
		SharedPreferences sp = context.getSharedPreferences(name, 0);
		return sp;
	}

	public static int getIntSp(Context context, int strId) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		int value = sp.getInt(key, 0);
		return value;
	}
	
	public static int getIntSp(Context context, int strId, int defaultValue) {
        String key = context.getResources().getString(strId);
        SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
        int value = sp.getInt(key, defaultValue);
        return value;
    }

	public static int getStraightIntSp(Context context, String key) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		int value = sp.getInt(key, 0);
		return value;
	}

	public static void writeIntSp(Context context, int strId, int value) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void writeStraightIntSp(Context context, String key, int value) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static String getStringSp(Context context, int strId) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		String value = sp.getString(key, "");
		return value;
	}

	public static String getStraightStringSp(Context context, String key) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		String value = sp.getString(key, "");
		return value;
	}

	public static void writeStringSp(Context context, int strId, String value) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void writeStraightStringSp(Context context, String key, String value) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static boolean getBooleanSp(Context context, int strId, boolean defaultValue) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		boolean value = sp.getBoolean(key, defaultValue);
		return value;
	}

	public static void writeBooleanSp(Context context, int strId, boolean value) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static float getFloatSp(Context context, int strId) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		float value = sp.getFloat(key, 0);
		return value;
	}
	
	public static float getFloatSp(Context context, int strId, float def) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		float value = sp.getFloat(key, def);
		return value;
	}

	public static void writeFloatSp(Context context, int strId, float value) {
		String key = context.getResources().getString(strId);
		writeFloatSp(context, key, value);
	}
	
	public static void writeFloatSp(Context context, String key, float value) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static long getLongSp(Context context, int strId) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		long value = sp.getLong(key, 0);
		return value;
	}

	public static void writeLongSp(Context context, int strId, long value) {
		String key = context.getResources().getString(strId);
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static long getStraightLongSp(Context context, String key) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		long value = sp.getLong(key, 0);
		return value;
	}

	public static void writeStraightLongSp(Context context, String key, long value) {
		SharedPreferences sp = getSharedPreferenceWithKey(context, R.string.key_sharedpreference_name);
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
}
