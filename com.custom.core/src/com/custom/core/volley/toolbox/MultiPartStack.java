package com.custom.core.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;

import com.custom.core.volley.AuthFailureError;
import com.custom.core.volley.Request;
import com.custom.core.volley.Request.Method;
import com.custom.core.volley.toolbox.HttpClientStack.HttpPatch;


/**
 * @author ZhiCheng Guo
 * @version 2014年10月7日 上午11:00:52 这个Stack用于上传文件, 如果没有这个Stack, 则上传文件不成功
 */
public class MultiPartStack extends HurlStack {  
    @SuppressWarnings("unused")  
    private static final String TAG = MultiPartStack.class.getSimpleName();  
    private final static String HEADER_CONTENT_TYPE = "Content-Type";  
      
      
      
      
    @Override  
    public HttpResponse performRequest(Request<?> request,  
            Map<String, String> additionalHeaders) throws IOException, AuthFailureError {  
          
        if(!(request instanceof MultiPartRequest)) { 
            return super.performRequest(request, additionalHeaders);  
        }  
        else {  
            return performMultiPartRequest(request, additionalHeaders);  
        }  
    }  
      
    private static void addHeaders(HttpUriRequest httpRequest, Map<String, String> headers) {  
        for (String key : headers.keySet()) {  
            httpRequest.setHeader(key, headers.get(key));  
        }  
    }  
      
    public HttpResponse performMultiPartRequest(Request<?> request,  
            Map<String, String> additionalHeaders)  throws IOException, AuthFailureError {  
//        HttpUriRequest httpRequest = createMultiPartRequest(request, additionalHeaders);  
//        addHeaders(httpRequest, additionalHeaders);  
//        addHeaders(httpRequest, request.getHeaders());  
//        HttpParams httpParams = httpRequest.getParams();  
//        int timeoutMs = request.getTimeoutMs();  
        // TODO: Reevaluate this connection timeout based on more wide-scale  
        // data collection and possibly different for wifi vs. 3G.  
//        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);  
//        HttpConnectionParams.setSoTimeout(httpParams, timeoutMs);  
        int timeoutMs = request.getTimeoutMs();  
        MultiPartRequest multiRequest =(MultiPartRequest)request;
        String BOUNDARY = java.util.UUID.randomUUID().toString();
		String MULTIPART_FROM_DATA = "multipart/form-data";
		DataOutputStream outStream=null;
		HttpURLConnection conn =null;
		URL uri = new URL(request.getUrl());
		conn = (HttpURLConnection) uri.openConnection();
//		conn = openConnection(uri, request);
		conn.setReadTimeout(3000); // cache max time
		conn.setConnectTimeout(5000);
		conn.setDoOutput(true);// allow output
		conn.setUseCaches(false); // cache is disable
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
		conn.connect();
		outStream = new DataOutputStream(conn.getOutputStream());
		// send data
		Map<String,File> files=multiRequest.getFileUploads();
		if (files != null) {
			writeFileParams(outStream, BOUNDARY, files);
		}
		Map<String,String> params=multiRequest.getStringUploads();
		// construct params for Text
		if(params!=null){
			writeStringParams(outStream, BOUNDARY, params);
		}
		String PREFIX = "--", LINEND = "\r\n";
		// the finsh flag
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		
		ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = conn.getResponseCode();
        if (responseCode == -1) {
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
        		conn.getResponseCode(), conn.getResponseMessage());
        BasicHttpResponse response = new BasicHttpResponse(responseStatus);
        response.setEntity(entityFromConnection(conn));
        for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
        return response;
        
//        /* Make a thread safe connection manager for the client */  
//        HttpClient httpClient = new DefaultHttpClient(httpParams);  
//  
//        return httpClient.execute(httpRequest);  
    }  
    //普通字符串数据
   	private void writeStringParams(DataOutputStream outputStream,String boundary,Map<String, String> textParams) throws IOException {
   		Set<String> keySet = textParams.keySet();
   		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
   			String name = it.next();
   			String value = textParams.get(name);
   			outputStream.writeBytes("--" + boundary + "\r\n");
   			outputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
   			outputStream.writeBytes("\r\n");
   			outputStream.writeBytes(URLEncoder.encode(value, "UTF-8") + "\r\n");
   		}
   	}
      /**
       * 文件数据
       * @param outputStream
       * @param boundary
       * @param fileparams
       * @throws Exception
       */
   	private void writeFileParams(DataOutputStream outputStream,String boundary,Map<String, File> fileparams) throws IOException {
   		Set<String> keySet = fileparams.keySet();
   		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
   			String name = it.next();
   			File value = fileparams.get(name);
   			outputStream.writeBytes("--" + boundary + "\r\n");
   			outputStream.writeBytes("Content-Disposition: form-data; name=\"" + name
   					+ "\"; filename=\"" +URLEncoder.encode(value.getName(), "UTF-8") + "\"\r\n");
   			outputStream.writeBytes("Content-Type: " + "image/jpg" + "\r\n");
   			outputStream.writeBytes("\r\n");
   			outputStream.write(getBytes(value));
   			outputStream.writeBytes("\r\n");
   		}
   	}
    //把文件转换成字节数组
  	private byte[] getBytes(File f) throws IOException {
  		FileInputStream in = new FileInputStream(f);
  		ByteArrayOutputStream out = new ByteArrayOutputStream();
  		byte[] b = new byte[1024];
  		int n;
  		while ((n = in.read(b)) != -1) {
  			out.write(b, 0, n);
  		}
  		in.close();
  		return out.toByteArray();
  	}  
  
    static HttpUriRequest createMultiPartRequest(Request<?> request,  
            Map<String, String> additionalHeaders) throws AuthFailureError {  
        switch (request.getMethod()) {  
            case Method.DEPRECATED_GET_OR_POST: {  
                // This is the deprecated way that needs to be handled for backwards compatibility.  
                // If the request's post body is null, then the assumption is that the request is  
                // GET.  Otherwise, it is assumed that the request is a POST.  
                byte[] postBody = request.getBody();  
                if (postBody != null) {  
                    HttpPost postRequest = new HttpPost(request.getUrl());  
                    if(request.getBodyContentType() != null)  
                        postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());  
                    HttpEntity entity;  
                    entity = new ByteArrayEntity(postBody);  
                    postRequest.setEntity(entity);  
                    return postRequest;  
                } else {  
                    return new HttpGet(request.getUrl());  
                }  
            }  
            case Method.GET:  
                return new HttpGet(request.getUrl());  
            case Method.DELETE:  
                return new HttpDelete(request.getUrl());  
            case Method.POST: {  
                HttpPost postRequest = new HttpPost(request.getUrl());  
                postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());  
                setMultiPartBody(postRequest,request);  
                return postRequest;  
            }  
            case Method.PUT: {  
                HttpPut putRequest = new HttpPut(request.getUrl());  
                if(request.getBodyContentType() != null)  
                    putRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());  
                setMultiPartBody(putRequest,request);  
                return putRequest;  
            }  
            // Added in source code of Volley libray.  
            case Method.PATCH: {  
                HttpPatch patchRequest = new HttpPatch(request.getUrl());  
                if(request.getBodyContentType() != null)  
                    patchRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());  
                return patchRequest;  
            }  
            default:  
                throw new IllegalStateException("Unknown request method.");  
        }  
    }  
      
    /**  
     * If Request is MultiPartRequest type, then set MultipartEntity in the  
     * httpRequest object.  
     *   
     * @param httpRequest  
     * @param request  
     * @throws AuthFailureError  
     */  
    private static void setMultiPartBody(HttpEntityEnclosingRequestBase httpRequest,  
            Request<?> request) throws AuthFailureError {  
  
        // Return if Request is not MultiPartRequest  
        if (!(request instanceof MultiPartRequest)) {  
            return;  
        }  
  
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
  
        /* example for setting a HttpMultipartMode */  
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);  
  
        // Iterate the fileUploads  
        Map<String, File> fileUpload = ((MultiPartRequest) request).getFileUploads();  
        for (Map.Entry<String, File> entry : fileUpload.entrySet()) {  
  
            builder.addPart(((String) entry.getKey()), new FileBody((File) entry.getValue()));  
        }  
  
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);  
        // Iterate the stringUploads  
        Map<String, String> stringUpload = ((MultiPartRequest) request).getStringUploads();  
        for (Map.Entry<String, String> entry : stringUpload.entrySet()) {  
            try {  
                builder.addPart(((String) entry.getKey()),  
                        new StringBody((String) entry.getValue(), contentType));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        httpRequest.setEntity(builder.build());  
    }  
  
}  