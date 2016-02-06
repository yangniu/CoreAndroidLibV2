package com.umeng.share;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareService implements SnsPostListener{

	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constants.DESCRIPTOR);

	private ShareCallBack shareCallBack;
	private Activity mActivity;

	public ShareService(Activity activity) {

		this.mActivity = activity;
		
	}

	public void setShareCallBack(ShareCallBack shareCallBack) {
		this.shareCallBack = shareCallBack;
	}

	public void showSharePlantDialog(ShareInfoVO shareInfo) {
		
		// 配置需要分享的相关平台
		List<SHARE_MEDIA> list = configPlatforms();

		if (list.isEmpty()) {
			Toast.makeText(mActivity, "抱歉，您未安装任何分享平台", 0).show();
			return;
		}
		
		mController.getConfig()
				.setPlatforms(list.toArray(new SHARE_MEDIA[] {}));

		mController.openShare(this.mActivity, false);
		// 设置分享的内容
		setShareContent(shareInfo);
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	private void setShareContent(ShareInfoVO shareInfo) {

		if (shareInfo == null) {

			Toast.makeText(mActivity, "请填写分享内容", 0).show();
			return;
		}
		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.setShareContent(shareInfo.getContent());
		UMImage urlImage = null;
		if (!TextUtils.isEmpty(shareInfo.getImageUrl())) {
			urlImage = new UMImage(this.mActivity, shareInfo.getImageUrl());
		} else if (shareInfo.getResImageId() != 0) {
			urlImage = new UMImage(this.mActivity, shareInfo.getResImageId());
		}
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(shareInfo.getContent());
		weixinContent.setTitle(shareInfo.getTitle());
		weixinContent.setTargetUrl(shareInfo.getTargetUrl());
		if (urlImage != null)
			weixinContent.setShareMedia(urlImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(shareInfo.getContent());
		circleMedia.setTitle(shareInfo.getTitle());
		if (urlImage != null)
			circleMedia.setShareMedia(urlImage);
		circleMedia.setTargetUrl(shareInfo.getTargetUrl());
		mController.setShareMedia(circleMedia);

//		QQShareContent qqShareContent = new QQShareContent();
//		qqShareContent.setShareContent(shareInfo.getContent());
//		if (urlImage != null)
//			qqShareContent.setShareMedia(urlImage);
//		qqShareContent.setTitle(shareInfo.getTitle());
//		qqShareContent.setTargetUrl(shareInfo.getTargetUrl());
//		mController.setShareMedia(qqShareContent);
//
//		QZoneShareContent qzoneShareContent = new QZoneShareContent();
//		qzoneShareContent.setShareContent(shareInfo.getContent());
//		if (urlImage != null){
//			qzoneShareContent.setShareMedia(urlImage);
//			qzoneShareContent.setShareImage(urlImage);
//		}
//		qzoneShareContent.setTitle(shareInfo.getTitle());
//		qzoneShareContent.setTargetUrl(shareInfo.getTargetUrl());
//		mController.setShareMedia(qzoneShareContent);
		
		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setShareContent(shareInfo.getContent());
		if (urlImage != null)
			sinaContent.setShareMedia(urlImage);
		mController.setShareMedia(sinaContent);
		
		 // 设置短信分享内容
        SmsShareContent sms = new SmsShareContent();
        sms.setShareContent(shareInfo.getTitle()+shareInfo.getContent() + "链接地址："+shareInfo.getTargetUrl());
        if (urlImage != null)
        	sms.setShareImage(urlImage);
        mController.setShareMedia(sms);
		
		mController.registerListener(this);
	
	}
	
	public void onDestroy(){
		if(mController!=null){
			mController.unregisterListener(this);
		}
	}
	
	/**
	 * 配置分享平台参数</br>
	 */
	private List<SHARE_MEDIA> configPlatforms() {
		List<SHARE_MEDIA> list = new ArrayList<SHARE_MEDIA>();
		//if (isApkInstalled(mActivity, "com.sina.weibo")) {
			// 添加新浪SSO授权
//			SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
//			mController.getConfig().setSsoHandler(sinaSsoHandler);
//			list.add(SHARE_MEDIA.SINA);
		//}
//		if (isApkInstalled(mActivity, "com.tencent.mobileqq")) {
//			// 添加QQ、QZone平台
//			String[] qqArr = Constants.getQQAppIdAndKey(mActivity);
//			if (qqArr == null || qqArr.length != 2) {
//				Toast.makeText(mActivity, "qq appId appkey 不合法", 0).show();
//				return list;
//			}
//			String qqAppId = qqArr[0];
//			String qqAppKey = qqArr[1];
//
//			// 添加QQ支持, 并且设置QQ分享内容的target url
//			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this.mActivity,
//					qqAppId, qqAppKey);
//			qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
//			qqSsoHandler.addToSocialSDK();
//			list.add(SHARE_MEDIA.QQ);
//			
//			// 添加QZone平台
//			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this.mActivity,
//					qqAppId, qqAppKey);
//			qZoneSsoHandler.setTargetUrl("http://www.umeng.com/social");
//			qZoneSsoHandler.addToSocialSDK();
//			
//			list.add(SHARE_MEDIA.QZONE);
//		}
//		if (isApkInstalled(mActivity, "com.tencent.mm")) {
			// 添加微信、微信朋友圈平台
			String[] wxarr = Constants.getWeixinAppIdAndSecret(mActivity);
			if (wxarr == null || wxarr.length != 2) {
				Toast.makeText(mActivity, "wx appId appkey 不合法", 0).show();
				return list;
			}
			String wxappId = wxarr[0];
			String wxAppSecret = wxarr[1];
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(this.mActivity, wxappId,
					wxAppSecret);
			if (wxHandler.isClientInstalled()) {
				wxHandler.addToSocialSDK();
				list.add(SHARE_MEDIA.WEIXIN);
			}

			// 支持微信朋友圈
			UMWXHandler wxCircleHandler = new UMWXHandler(this.mActivity,
					wxappId, wxAppSecret);
			if (wxCircleHandler.isClientInstalled()) {
				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();

				list.add(SHARE_MEDIA.WEIXIN_CIRCLE);
			}
//		}
//		SmsHandler smsHandler =new SmsHandler();
//		smsHandler.addToSocialSDK();
//		list.add(SHARE_MEDIA.SMS);
		
		return list;
	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private boolean addQQQZonePlatform() {
		String[] arr = Constants.getQQAppIdAndKey(mActivity);
		if (arr == null || arr.length != 2) {
			Toast.makeText(mActivity, "qq appId appkey 不合法", 0).show();
			return false;
		}

		String appId = arr[0];
		String appKey = arr[1];

		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this.mActivity, appId,
				appKey);
		if (qqSsoHandler.isClientInstalled()) {
			qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
			qqSsoHandler.addToSocialSDK();

		}
		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this.mActivity,
				appId, appKey);
		qZoneSsoHandler.setTargetUrl("http://www.umeng.com/social");
		qZoneSsoHandler.addToSocialSDK();
		return true;
	}

	public  boolean isApkInstalled(Context context,
			String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	public 	void onActivityResult(int requestCode, int resultCode, Intent data){
		/**使用SSO授权必须添加如下代码 */
		if(mController!=null){
		    UMSsoHandler ssoHandler =mController.getConfig().getSsoHandler(requestCode) ;
		    if(ssoHandler != null){
		       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
		}
	}
	public UMSocialService getController() {
		return mController;
	}

	@Override
	public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
		SharePlant sharePlant = null;
		if (shareCallBack != null) {
			switch (arg0) {
			case SINA:
				sharePlant = SharePlant.sina;
				break;
			case QQ:
				sharePlant = SharePlant.qq;
				break;
			case QZONE:
				sharePlant =SharePlant.qzone;
				break;
			case SMS:
				sharePlant =SharePlant.sms;
				break;
			case WEIXIN:
				sharePlant = SharePlant.wexin;
				break;
			case WEIXIN_CIRCLE:
				sharePlant = SharePlant.wxfriend;
				break;
			}
			shareCallBack.onComplete(sharePlant,arg1 == StatusCode.ST_CODE_SUCCESSED);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
	
	
}
