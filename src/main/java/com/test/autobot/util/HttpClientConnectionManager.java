package com.test.autobot.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpClientConnectionManager {

	@Value("${httpclient.pool.max.total:1000}")
	private int maxTotal;

	@Value("${httpclient.pool.max.per.route:500}")
	private int maxPerRoute;

	private PoolingHttpClientConnectionManager connectionManager;

	@PostConstruct
	public void init() {
		connectionManager = new PoolingHttpClientConnectionManager(60000, TimeUnit.MILLISECONDS);
		connectionManager.setMaxTotal(maxTotal);
		connectionManager.setDefaultMaxPerRoute(maxPerRoute);
	}

	public PoolingHttpClientConnectionManager get() {
		return connectionManager;
	}
}
