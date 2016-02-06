package com.custom.core.volley.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.custom.core.volley.AuthFailureError;
import com.custom.core.volley.NetworkResponse;
import com.custom.core.volley.ParseError;
import com.custom.core.volley.Request;
import com.custom.core.volley.Response;
import com.custom.core.volley.Response.ErrorListener;
import com.custom.core.volley.Response.Listener;
import com.custom.core.volley.toolbox.HttpHeaderParser;
import com.custom.core.volley.toolbox.RequestFuture;




public class NFRequest extends Request<BaseParser> {

	/** Default charset for JSON request. */
	protected final String PROTOCOL_CHARSET = "utf-8";

	private BaseParser parser;
	private Map<String, String> headers;
	private final Listener<BaseParser> mListener;
	private final Map<String, String> mRequestBody;

	/**
	 * Post请求构造方法。
	 * @param url	请求URL
	 * @param Map<String,String>Post请求
	 * @param parser	请求结果解析parse，如果不需要解析，则传空
	 * @param callBack	回调
	 */
	public NFRequest(String url, Map<String, String> requestBody,
			final BaseParser parser, ErrorListener errorListener,Listener<BaseParser> listener) {
		super(Method.POST, url, errorListener);
		mListener =listener;
		this.parser = parser;
		this.mRequestBody = requestBody;
		setShouldCache(false);
	}
	
	/**
	 * Get请求专用  
	 * @param url		请求URL
	 * @param parser	请求结果解析parse，如果不需要解析，则传空
	 * @param callBack	回调
	 */
	public NFRequest(String url, final BaseParser parser,ErrorListener errorListener,Listener<BaseParser> listener) {
		super(Method.GET, url,errorListener);
		mListener = listener;
		this.parser = parser;
		this.mRequestBody = null;
		setShouldCache(false);
	}

	/**
	 * 同步请求
	 * 
	 * @param method
	 * @param url
	 * @param requestBody
	 * Get请求传Null。Post请求传URLEncodedUtils.format(pairs, "UTF-8").
	 * @param parser
	 * @param futureSuc
	 * @param futureError
	 */
	public NFRequest(int method, String url, Map<String, String> requestBody,
			final BaseParser parser, RequestFuture<BaseParser> futureSuc,
			RequestFuture<BaseParser> futureError) {
		super(method, url, futureError);
		mListener = futureSuc;
		this.parser = parser;
		this.mRequestBody = requestBody;
		setShouldCache(false);
	}

	public void setHeader(Map<String, String> header) {
		this.headers = header;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (headers == null) {
			return super.getHeaders();
		}
		return headers;
	}

	@Override
	protected Response<BaseParser> parseNetworkResponse(NetworkResponse response) {
		try {
			String charset = HttpHeaderParser.parseCharset(response.headers);
			String json = new String(response.data,
					charset);
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
		mListener.onResponse(response);
	}
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return this.mRequestBody == null?null:this.mRequestBody;
	}

}
