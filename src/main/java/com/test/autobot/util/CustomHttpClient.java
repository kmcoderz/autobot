package com.test.autobot.util;

import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.autobot.exception.HttpClientException;

public class CustomHttpClient implements Client {

	protected String baseUrl;

	private static final int TIMEOUT = 1800000;

	private Logger logger = Logger.getLogger(CustomHttpClient.class.getName());

	protected HttpClientConnectionManager lkHttpClientConnectionManager;

	public CustomHttpClient() {
		super();
	}

	public CustomHttpClient(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
	}

	@Override
	public Response execute(Request request) throws Exception {
		try {
			switch (request.getHttpMethod()) {
			case GET:
				return sendGetRequest(request);
			case POST:
				return sendPostRequest(request);
			default:
				break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;

	}

	private Response sendGetRequest(Request request) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = getHttpClient();
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			Response response = new Response();

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();

			HttpGet get = new HttpGet(baseUrl + request.getUrl());
			get.setConfig(requestConfig);

			closeableHttpResponse = closeableHttpClient.execute(get);

			response.setResponseCode(HttpStatus.valueOf(closeableHttpResponse.getStatusLine().getStatusCode()));
			response.setResponseContent(EntityUtils.toString(closeableHttpResponse.getEntity()));
			return response;
		} finally {
			try {
				if (closeableHttpResponse != null) {
					closeableHttpResponse.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	}

	private Response sendPostRequest(Request request) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = getHttpClient();
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			Response response = new Response();

			HttpPost post = new HttpPost(baseUrl + request.getUrl());

			// request config
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
			post.setHeader("Content-Type", request.getContentType());
			post.setConfig(requestConfig);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request.getRequestPayload());
			StringEntity input = new StringEntity(json);
			input.setContentType("application/json");

			post.setEntity(input);

			// HttpResponse httpResponse = closeableHttpClient.execute(post);
			closeableHttpResponse = closeableHttpClient.execute(post);

			response.setResponseCode(HttpStatus.valueOf(closeableHttpResponse.getStatusLine().getStatusCode()));
			response.setResponseContent(EntityUtils.toString(closeableHttpResponse.getEntity()));
			return response;
		} finally {
			try {
				if (closeableHttpResponse != null) {
					closeableHttpResponse.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public CloseableHttpClient getHttpClient() {
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return 10 * 1000;
			}
		};
		return HttpClients.custom().addInterceptorFirst(new RequestInterceptor()).addInterceptorFirst(new ResponseInterceptor()).setKeepAliveStrategy(myStrategy)
				.setConnectionManager(lkHttpClientConnectionManager.get()).build();
	}

	private class RequestInterceptor implements HttpRequestInterceptor {

		@Override
		public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
			logger.info(request);
		}

	}

	private class ResponseInterceptor implements HttpResponseInterceptor {

		@Override
		public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
			logger.info(response);
			if (response != null && response.getStatusLine() != null) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()) {
					// Server is down
					throw new HttpClientException("Remote server is currently unable to serve requests.");
				} else if (response.getStatusLine().getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					// Error from server

				}
			}
		}

	}
}
