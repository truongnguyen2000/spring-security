package com.truong.common.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class HttpClientService {

	public static <T> T get(String url, Map<String, String> headers, Map<String, String> params, Class<T> objectclass,
			int timeout) {
		try {

			  CloseableHttpClient httpClient = HttpClients.createDefault();

			URIBuilder builder = new URIBuilder(url);

			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.setParameter(entry.getKey().toString(), entry.getValue().toString());
			}

			HttpGet httpGet = new HttpGet(builder.build());
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpGet.addHeader(entry.getKey().toString(), entry.getValue().toString());
			}

			CloseableHttpResponse response = httpClient.execute(httpGet);

			String jsonString = EntityUtils.toString(response.getEntity());

			ObjectMapper mapper = new ObjectMapper();
			T data = (T) mapper.readValue(jsonString, objectclass);

			return data;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static <T> T post(String url, Map<String, String> headers, Object params, Class<T> objectclass,
			int timeout) {
		try {
			 CloseableHttpClient httpClient = HttpClients.createDefault();

			URIBuilder builder = new URIBuilder(url);

			HttpPost httpPost = new HttpPost(builder.build());
			httpPost.addHeader("Content-Type", "application/json");
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
			}

			ObjectMapper mapper = new ObjectMapper();
			String jsonParams = mapper.writeValueAsString(params);
			StringEntity requestEntity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);

			httpPost.setEntity(requestEntity);

			CloseableHttpResponse response = httpClient.execute(httpPost);

			String jsonString = EntityUtils.toString(response.getEntity());

			T data = (T) mapper.readValue(jsonString, objectclass);

			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	public static <T> T formUrlEncoded(String url, Map<String, String> headers, Map<String, String> params,
			Class<T> objectclass, int timeout) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			URIBuilder builder = new URIBuilder(url);

			HttpPost httpPost = new HttpPost(builder.build());

			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
			}

			List<NameValuePair> formParams = params.entrySet().stream()
					.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());

			httpPost.setEntity(new UrlEncodedFormEntity(formParams));

			CloseableHttpResponse response = httpClient.execute(httpPost);

			String jsonString = EntityUtils.toString(response.getEntity());

			ObjectMapper mapper = new ObjectMapper();
			T data = (T) mapper.readValue(jsonString, objectclass);

			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
