/**
 * 
 * @author heqing1
 * @version 2012-10-18
 */
package com.custom.core.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class CommonServiceUtil {
	/**
	 * 判断sim卡是否准备好
	 * 注意：需添加权限 android.permission.READ_PHONE_STATE
	 * @param context
	 * @return sim卡是否可用
	 */
	public static boolean isSIMReady(Context context){
		TelephonyManager teleManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return TelephonyManager.SIM_STATE_READY==teleManager.getSimState();
	}
	/**
	 * 隐藏控件键盘
	 * @param context
	 * @param view
	 */
	public static Boolean hideInputMethod(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return false;
	}
	public static Boolean showInputMethod(Context context, View v) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		if (imm != null) {
			return imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
		}
		return false;
	}
	/**
	 * 判断键盘是否激活
	 * @param context
	 * @return 键盘激活状态
	 */
	public static boolean isSoftKeyBoardActive(Context context){
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();
	}
}
