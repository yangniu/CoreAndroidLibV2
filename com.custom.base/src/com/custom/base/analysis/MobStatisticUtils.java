package com.custom.base.analysis;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.support.v4.app.Fragment;
/***
 * 统计事件
 * @author niufei
 *
 */
public class MobStatisticUtils {

	private MobStatisticUtils() {
	}
	
	public static void onResume(Context context){
		
		MobclickAgent.onResume(context);
	}
	public static void onFragResume(Fragment fragment){
		MobclickAgent.onPageStart(fragment.getClass().getSimpleName()); 
	}
	
	public static void onPause(Context context){
		MobclickAgent.onPause(context);
	}
	public static void onFragPause(Fragment fragment){
		MobclickAgent.onPageEnd(fragment.getClass().getSimpleName()); 
	}
	
	
	public static void onEvent(Context context,String value){
		MobclickAgent.onEvent(context, value);
	}
	
	public static void onEvent(Context context,String value,String param){
		MobclickAgent.onEvent(context, value, param);
	}
}
