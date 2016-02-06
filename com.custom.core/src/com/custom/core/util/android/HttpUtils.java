package com.custom.core.util.android;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.custom.core.base64.Md5Encrypt;



/**
 * @Company SINA
 *
 * @Copyright 2011-2012
 *
 * @author LIU ZHONG LEI
 *
 * @date 2011-10-24 下午5:40:34
 *
 * @Version 1.0
 */
public class HttpUtils {
	
	private static final String app_key = "CDcbncdfmewrwerVF";
	private static final String app_secret = "23dlbnb23BGfasfsRVFdopJmlihr";

	public static String getURL(String url,Map<String, String> params){
		StringBuilder entityBuilder = new StringBuilder("");

		if (url != null) {
			if (url.endsWith("?")) {
				entityBuilder.append(url);
			} else {
				entityBuilder.append(url + "?");
			}
		}

		if (params != null && !params.isEmpty()) {
			String value = null;
			Map.Entry<String, String> entry = null;
			for (Iterator<Map.Entry<String, String>> iter = params.entrySet()
					.iterator(); iter.hasNext();) {
				entry = iter.next();
				value = entry.getValue();
				if (value == null) {
					value = "";
				}
				entityBuilder.append(entry.getKey()).append('=');
				entityBuilder.append(URLEncoder.encode(value));
				entityBuilder.append('&');
			}
		}

		if (entityBuilder.toString().endsWith("&")) {
			entityBuilder.deleteCharAt(entityBuilder.length() - 1);
		}

		return entityBuilder.toString();
	}
	
	public static String generateURL(String url, Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return "";
		}
		String timestamp = String.valueOf(System.currentTimeMillis());
		String value = "";
		Map.Entry<String, String> entry = null;
		for (Iterator<Map.Entry<String, String>> iter = params.entrySet()
				.iterator(); iter.hasNext();) {
			entry = iter.next();
			value += entry.getKey() + entry.getValue();
		}
		String token = Md5Encrypt.md5(app_key + value + timestamp + app_secret);
		String result = url + "?" + "timestamp=" + timestamp + "&token="
				+ token + "&app_key=" + app_key;
		return result;
	}
	
}

