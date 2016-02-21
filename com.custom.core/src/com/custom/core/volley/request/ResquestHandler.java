package com.custom.core.volley.request;


public interface ResquestHandler<T>{

	
	public void onPreExcute();
	
	public void onSuccessPostExecute(T response);
	
	public void onFailurePostExecute(String failureMsg);
	
	public void onFailNetExecute();
	
	public void onCompleteExcute();
	
	
}
