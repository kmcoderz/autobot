package com.test.autobot.util;

import org.springframework.http.HttpStatus;

public class Response {
	private String responseContent;
	private HttpStatus responseCode;

	public Response() {
		super();
	}

	public Response(String responseContent, HttpStatus responseCode) {
		super();
		this.responseContent = responseContent;
		this.responseCode = responseCode;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public HttpStatus getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
}
