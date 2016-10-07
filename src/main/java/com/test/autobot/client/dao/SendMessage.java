package com.test.autobot.client.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.autobot.util.Client;
import com.test.autobot.util.Request;
import com.test.autobot.util.Response;

@Service
public class SendMessage {

	@Resource(name = "facebookClient")
	Client fbClient;

	@Value("${facebook.page.access.token}")
	private String accessToken;

	public HttpStatus sendMessage(Map<String, Object> payload) {
		Request request = new Request();
		if (payload != null) {
			try {
				request.setUrl("/messages?access_token=" + accessToken);
				request.setHttpMethod(HttpMethod.POST);
				request.setRequestPayload(payload);
				request.setContentType("application/json");
				Response response = fbClient.execute(request);
				return response.getResponseCode();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.error("SendMessage Error");
		}
		return HttpStatus.BAD_REQUEST;
	}

	private static final Logger logger = Logger.getLogger(SendMessage.class.getName());
}
