package com.custom.core.volley.request;

import com.custom.core.exception.NFRuntimeException;
import com.custom.core.volley.Request;
import com.custom.core.volley.RequestQueue;
import com.custom.core.volley.toolbox.Volley;

import android.content.Context;
import android.text.TextUtils;

/**
 * 
 * ClassName: VolleyRequestManager <br/>
 * date: 2015-3-23 下午3:09:53 <br/>
 *
 * @version
 */
public class VolleyRequestManager {

	private static RequestQueue mRequestQueue;
	
	private static VolleyRequestManager requestManager=new VolleyRequestManager();
	
	public static void initialize(Context context) {
		
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}
	}
	
	
	private VolleyRequestManager() {
	}
	
	public static VolleyRequestManager getInstance(){
		return requestManager;
	}
	/***
	 * addRequest:加到同一个RequestQueue中。
	 * @param request
	 * @param tag    建议使用页面的class.getName,需保证此变量与其它页面请求不能重复 此方法是针对全局唯一队列进行取消
	 */
	public  void addRequest(Request<?> request,String tag) {
		if (request == null) {
			return;
		}
		if(mRequestQueue ==null){
			throw new NFRuntimeException("please initialize VolleyRequestManager in Application.... ");
		}
		request.setTag(tag);
		mRequestQueue.addRequest(request);
	}


	/**
	 * 
	 * cancelAllRequest:退出应用时间取消所有请求。 此方法是针对全局唯一队列进行取消
	 *
	 * @author ybq
	 */
	public  void cancelAllRequest() {
		if(mRequestQueue==null) {
			throw new NFRuntimeException("please initialize VolleyRequestManager in Application.... ");
		}
		mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
		// 关闭线程
		mRequestQueue.stop();
	}

	/**
	 * 
	 * cancelRequestByTag:(这里用一句话描述这个方法的作用)
	 * 建议使用页面的class.getName,需保证此变量与其它页面请求不能重复 此方法是针对全局唯一队列进行取消
	 *
	 * @author ybq
	 * @param tag
	 */
	public  void cancelRequestByTag(String tag) {
		if (TextUtils.isEmpty(tag)) {
			return;
		}
		if(mRequestQueue ==null){
			throw new NFRuntimeException("please initialize VolleyRequestManager in Application.... ");
		}
		mRequestQueue.cancelAll(tag);
	}


}
