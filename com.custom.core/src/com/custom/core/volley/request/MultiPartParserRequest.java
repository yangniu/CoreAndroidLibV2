package com.custom.core.volley.request;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.custom.core.volley.NetworkResponse;
import com.custom.core.volley.ParseError;
import com.custom.core.volley.Request;
import com.custom.core.volley.Response;
import com.custom.core.volley.Response.ErrorListener;
import com.custom.core.volley.Response.Listener;
import com.custom.core.volley.toolbox.HttpHeaderParser;
import com.custom.core.volley.toolbox.MultiPartRequest;





/**
 * MultipartRequest - To handle the large file uploads.
 * Extended from JSONRequest. You might want to change to StringRequest based on your response type.
 * @author Mani Selvaraj
 *
 */
public class MultiPartParserRequest extends Request<BaseParser> implements MultiPartRequest{

	private final Listener<BaseParser> mListener;
	/* To hold the parameter name and the File to upload */
	private Map<String,File> fileUploads = new HashMap<String,File>();
	
	/* To hold the parameter name and the string content to upload */
	private Map<String,String> stringUploads = new HashMap<String,String>();
	private BaseParser parser;
   
	public MultiPartParserRequest(String url,ErrorListener errorListener, Listener<BaseParser> listener,BaseParser parser) {
		super(Method.POST, url, errorListener);
		this.mListener =listener;
		this.parser = parser;
	}

    public void addFileUpload(String param,File file) {
    	fileUploads.put(param,file);
    }
    
    public void addStringUpload(String param,String content) {
    	stringUploads.put(param,content);
    }
    
    /**
     * 要上传的文件
     */
    public Map<String,File> getFileUploads() {
    	return fileUploads;
    }
    
    /**
     * 要上传的参数
     */
    public Map<String,String> getStringUploads() {
    	return stringUploads;
    }

    @Override
    protected Response<BaseParser> parseNetworkResponse(NetworkResponse response) {
    	try {
	    	String charset = HttpHeaderParser.parseCharset(response.headers);
	    	String json = new String(response.data,charset);
			parser.parse(json);
			Response<BaseParser> success = Response.success(parser,
					HttpHeaderParser.parseCacheHeaders(response));
			return success;
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
    }

	@Override
	protected void deliverResponse(BaseParser response) {
		if(mListener != null) {
			mListener.onResponse(response);
		}
	}
	
	/**
	 * 空表示不上传
	 */
	 public String getBodyContentType() {
	        return "image/*";
	    }
}