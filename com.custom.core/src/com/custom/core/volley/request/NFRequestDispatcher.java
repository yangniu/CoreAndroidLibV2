package com.custom.core.volley.request;

import java.util.Map;

import com.custom.core.volley.Response.ErrorListener;
import com.custom.core.volley.Response.Listener;
import com.custom.core.volley.VolleyError;



/**
 * 任务分发器
 * @author leihen
 *
 */
public class NFRequestDispatcher {

	private static NFRequestDispatcher requestDispatcher;
	
	private NFRequestDispatcher() {
	}
	
	public static NFRequestDispatcher getInstance(){
		if(requestDispatcher ==null){
			synchronized (NFRequestDispatcher.class) {
				if(requestDispatcher==null){
					requestDispatcher=new NFRequestDispatcher();
				}
			}
		}
		return requestDispatcher;
	}
	
	/**
	 * 
	 * @param url
	 * @param callBack
	 * @param baseParser
	 * @return
	 */
	public NFRequest requestByGet(String url,final ResquestHandler<BaseParser> callBack,BaseParser baseParser){
		if(callBack!=null){
			callBack.onPreExcute();
		}
		return new NFRequest(url, baseParser, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(callBack!=null){
					callBack.onFailNetExecute();
					callBack.onCompleteExcute();
				}
			}
		}, new Listener<BaseParser>() {

			@Override
			public void onResponse(BaseParser response) {
				if(callBack!=null){
					if(response.getCode() ==BaseParser.SUCCESS){
						callBack.onSuccessPostExecute(response);
					}else{
						callBack.onFailurePostExecute(response.getMsg());
					}
					callBack.onCompleteExcute();
				}
			}
		});
	}
	/**
	 * 
	 * @param url
	 * @param requestBody
	 * @param callBack
	 * @param baseParser
	 * @return
	 */
	public NFRequest requestByPost(String url,Map<String, String> requestBody,final ResquestHandler<BaseParser> callBack,BaseParser baseParser){
		
		if(callBack!=null){
			callBack.onPreExcute();
		}
		return new NFRequest(url,requestBody, baseParser, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(callBack!=null){
					callBack.onFailNetExecute();
					callBack.onCompleteExcute();
				}
			}
		}, new Listener<BaseParser>() {

			@Override
			public void onResponse(BaseParser response) {
				if(callBack!=null){
					if(response.getCode() ==BaseParser.SUCCESS){
						callBack.onSuccessPostExecute(response);
					}else{
						callBack.onFailurePostExecute(response.getMsg());
					}
					callBack.onCompleteExcute();
				}
			}
		});
	}
	
	/***
	 * 上传
	 * @param url
	 * @param listener
	 * @param baseParser
	 * @return
	 */
	public MultiPartParserRequest uploadFileRequest(String url,final ResquestHandler<BaseParser> callBack,BaseParser baseParser){
		if(callBack!=null){
			callBack.onPreExcute();
		}
		return new MultiPartParserRequest(url, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(callBack!=null){
					callBack.onFailNetExecute();
					callBack.onCompleteExcute();
				}
			}
		}, new Listener<BaseParser>() {

			@Override
			public void onResponse(BaseParser response) {
				if(callBack!=null){
					if(response.getCode() ==BaseParser.SUCCESS){
						callBack.onSuccessPostExecute(response);
					}else{
						callBack.onFailurePostExecute(response.getMsg());
					}
					callBack.onCompleteExcute();
				}
			}
		},baseParser);
		
	}
	
//	public JsonObjectRequest requestJsonObj(int method,String url,JSONObject jsonRequest,Listener<JSONObject> listener){
//		
//		return new JsonObjectRequest(method, url, jsonRequest, listener, errorListener);s
//	}
//	
//	public JsonArrayRequest requestJsonArray(String url , Listener<JSONArray> listener){
//		
//		return new JsonArrayRequest(url, listener, errorListener);
//	}
//	
//	public StringRequest requestString(int method,String url , Listener<String> listener){
//		return	new StringRequest(method, url, listener, errorListener);
//	}
	
}
