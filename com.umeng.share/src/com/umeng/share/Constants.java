package com.umeng.share;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

public class Constants {
    
    public static final String DESCRIPTOR = "com.umeng.share";
	
	private static final String TIPS = "请移步官方网站 ";
	private static final String END_TIPS = ", 查看相关说明.";
	public static final String TENCENT_OPEN_URL = TIPS + "http://wiki.connect.qq.com/android_sdk使用说明" 
													+ END_TIPS;
	public static final String PERMISSION_URL = TIPS + "http://wiki.connect.qq.com/openapi权限申请" 
													+ END_TIPS;
	
	public static final String SOCIAL_LINK = "http://www.umeng.com/social";
	public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";
	public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";
	
	public static final String SOCIAL_CONTENT = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，我们简化了社交平台的接入，为开发者提供坚实的基础服务：（一）支持各大主流社交平台，" + 
												"（二）支持图片、文字、gif动图、音频、视频；@好友，关注官方微博等功能" +
												"（三）提供详尽的后台用户社交行为分析。http://www.umeng.com/social";
	
	/**
	 * 获取QQId
	 * @param context
	 * @return
	 */
	public static String[] getQQAppIdAndKey(Context context){
		String shareId =getMeString(context,"shareToQQAppID");
		String shareKey =getMeString(context,"shareToQQAppKey");
		if(shareId!=null&&shareId.startsWith("qq")){
			shareId=shareId.substring("qq".length());
		}
		if(shareKey!=null&&shareKey.startsWith("qq")){
			shareKey=shareKey.substring("qq".length());
		}
		
		return new String[]{shareId,shareKey};
	}
	
	/***
	 * 获取微信AppIdAndSecret
	 * @param context
	 * @return
	 */
	public static String[] getWeixinAppIdAndSecret(Context context) {
		String shareId=  getMeString(context,"shareToWXAppKey");
		String shareKey =getMeString(context,"shareToWXAppSecret");
		return new String[]{shareId,shareKey};
	}
	/***
	 * 
	 * 获取Meta_data
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getMeString(Context context,String key){
	    String value =null;
		try {
			ApplicationInfo	info = context.getPackageManager()
			           .getApplicationInfo(context.getPackageName(),
			           PackageManager.GET_META_DATA);
			 value=info.metaData.getString(key);
			 if(TextUtils.isEmpty(value)){
				 throw new RuntimeException("share Component find meta-data for" + key +"is null");   
			 }
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("share Component can not find meta-data for" + key);
		}
		return value;
	}
}
