package com.test.autobot.util;

import java.util.Map;

import org.springframework.http.HttpMethod;

public class Request {
	private String url;
	private HttpMethod httpMethod;
	private String contentType;
	private Map<String, Object> requestPayload;

	public Request() {
		super();
	}

	public Request(String url, HttpMethod httpMethod, String contentType, Map<String, Object> requestPayload) {
		super();
		this.url = url;
		this.httpMethod = httpMethod;
		this.contentType = contentType;
		this.requestPayload = requestPayload;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Map<String, Object> getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(Map<String, Object> requestPayload) {
		this.requestPayload = requestPayload;
	}
}
