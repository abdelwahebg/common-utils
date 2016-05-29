/**
 * Project Name:commons
 * File Name:HttpClientUtils.java
 * Package Name:com.github.becausetesting.http
 * Date:Apr 17, 20166:29:02 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.apache.logging.log4j.LogManager;

/**
 * ClassName:HttpClientUtils Function: TODO ADD FUNCTION. Reason: TODO ADD
 * REASON. Date: Apr 17, 2016 6:29:02 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class TempHttpClientUtils {

	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(TempHttpClientUtils.class);

	private static CloseableHttpClient httpClient = null;
	private static HttpResponse httpResponse = null;

	// default http request settings
	private static int DEFAULT_REQUEST_TIMEOUT = 1000;
	private static int DEFAULT_RETRY_TIMES = 0;

	private HttpRequestRetryHandler httpRequestRetryHandler;

	enum Type {
		GET, POST;
	}

	public InputStream getRequest(String url) {
		InputStream inputStream = null;
		HttpResponse httpResponse = getHttpClientResponse(url, Type.GET, null);
		HttpEntity responseEntity = httpResponse.getEntity();
		try {
			inputStream = responseEntity.getContent();
		} catch (UnsupportedOperationException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inputStream;
	}

	public InputStream postRequest(String url, List<NameValuePair> postdata) {
		InputStream responseContent = null;
		HttpResponse httpResponse = getHttpClientResponse(url, Type.POST, postdata);
		HttpEntity httpEntity = httpResponse.getEntity();
		try {
			responseContent = httpEntity.getContent();
		} catch (ParseException | IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public void setRetryTimes(int retrytime) {
		this.httpRequestRetryHandler = new HttpRequestRetryHandler() {

			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount > retrytime) {
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					// Timeout
					return false;
				}
				if (exception instanceof UnknownHostException) {
					// Unknown host
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					// Connection refused
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};

	}

	private void getHttpClient() {
		if (httpClient == null) {
			HttpClientBuilder hcb = null;
			if (this.httpRequestRetryHandler != null) {
				hcb = HttpClientBuilder.create().setRetryHandler(httpRequestRetryHandler);
				httpClient = hcb.build();
			} else {
				httpClient = HttpClientBuilder.create().build();
			}
		}
	}

	public HttpResponse getHttpClientResponse(String url, Type type, List<NameValuePair> postdata) {
		HttpGet httpGet = null;
		HttpPost httpPost = null;

		getHttpClient();
		RequestConfig defaultHttpHeaders = RequestConfig.custom().setSocketTimeout(DEFAULT_REQUEST_TIMEOUT)
				.setConnectTimeout(DEFAULT_REQUEST_TIMEOUT).build();
		try {
			switch (type) {
			case GET:
				httpGet = new HttpGet(url);
				httpGet.setConfig(defaultHttpHeaders);
				httpResponse = httpClient.execute(httpGet);
				break;
			case POST:
				httpPost = new HttpPost(url);
				httpPost.setConfig(defaultHttpHeaders);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postdata, Consts.UTF_8);
				httpPost.setEntity(entity);
				httpResponse = httpClient.execute(httpGet);
			default:
				httpGet = new HttpGet(url);
				httpGet.setConfig(defaultHttpHeaders);
				httpResponse = httpClient.execute(httpGet);
				break;
			}

			StatusLine statusLine = httpResponse.getStatusLine();
			/*
			 * ProtocolVersion httpProtocolVersion =
			 * statusLine.getProtocolVersion(); String reasonPhrase =
			 * statusLine.getReasonPhrase(); int statusCode =
			 * statusLine.getStatusCode();
			 */
			String allstatus = statusLine.toString();
			logger.info("Reponse http status: " + allstatus);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return httpResponse;
	}

	/**
	 * getCookies: refer http://blog.csdn.net/alibert/article/details/42788189
	 * 
	 * @author alterhu2020@gmail.com
	 * @since JDK 1.8
	 */
	public static void getCookies() {
		HeaderElementIterator headerIterator = new BasicHeaderElementIterator(
				httpResponse.headerIterator("Set-Cookie"));
		while (headerIterator.hasNext()) {
			HeaderElement cookiesElement = headerIterator.nextElement();
			logger.info(cookiesElement.getName() + " = " + cookiesElement.getValue());
			NameValuePair[] params = cookiesElement.getParameters();
			for (int i = 0; i < params.length; i++) {
				logger.info(" " + params[i]);
			}
		}

	}

}