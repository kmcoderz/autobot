package com.test.autobot.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.test.autobot.util.CustomHttpClient;
import com.test.autobot.util.HttpClientConnectionManager;

@Component("facebookClient")
public class FacebookClient extends CustomHttpClient {

	@Autowired
	private Environment environment;

	@Autowired
	private HttpClientConnectionManager connectionManager;

	@PostConstruct
	public void init() {
		baseUrl = environment.getProperty("facebook.messenger.base.url");
		lkHttpClientConnectionManager = connectionManager;
	}

}
