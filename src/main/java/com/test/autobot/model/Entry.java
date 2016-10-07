package com.test.autobot.model;

import java.util.Date;

public class Entry {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Messaging[] getMessaging() {
		return messaging;
	}

	public void setMessaging(Messaging[] messaging) {
		this.messaging = messaging;
	}

	private Long id;
	private Date time;
	private Messaging[] messaging;
}
