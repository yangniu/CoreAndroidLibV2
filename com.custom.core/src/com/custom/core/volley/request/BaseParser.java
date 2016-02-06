
package com.custom.core.volley.request;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * ClassName:BaseParser <br/>
 * Date: 2013-8-29 下午4:51:54 <br/>
 * 
 * @version
 * @since JDK 1.6
 * @see
 */
public class BaseParser {
	/**
	 * Error:默认值，网络错误.
	 */
	public final static int ERROR = -1;
	/**
	 * Failed:有网络连接但数据格式错误.
	 */
	public final static int FAILED = -2;
	/**
	 * Empty:在Success情况下数据为空，子类处理.
	 */
	public final static int EMPTY = -3;
	/**
	 * Success:服务器返回数据正常.
	 */
	public final static int SUCCESS = 1000;

	private int code = ERROR;
	private String msg;
	private String total;
	private JSONObject obj;
	
	private Gson mGson;

	public BaseParser() {
		mGson = new Gson();
	}

	public BaseParser(String json) {
		mGson = new Gson();
		parse(json);
	}

	public void parse(String json) {
		if (TextUtils.isEmpty(json)) {
			setCode(ERROR);
			return;
		}
		try {
			//Android 4.0及以上都已经在内部类中处理,Android 2.2至Android 2.3.3未作处理
			if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1){
				if (json != null && json.startsWith("\ufeff")) {
					json = json.substring(1);
				}
			}
			obj = new JSONObject(json);
			parseResult(obj);
		} catch (JSONException e) {
			setCode(ERROR);
			e.printStackTrace();
		}
	}

	protected void parseResult(JSONObject obj) {
		this.obj = obj;
		if (obj.has("code")) {
			parseStatus(obj);
		}
	}

	protected void parseStatus(JSONObject obj) {
		msg = obj.optString("msg");
		code = obj.optInt("code");
		total = obj.optString("total");
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		
		if(TextUtils.isEmpty(msg)){
			if(code==ERROR){
				msg="数据请求失败";
			}else if(code== FAILED){
				msg ="数据格式错误";
			}else if(code ==EMPTY){
				msg ="请求数据为空";	
			}
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public JSONObject getObj() {
		return obj;
	}
	public Gson getGson(){
		return mGson;
	}

	
}
