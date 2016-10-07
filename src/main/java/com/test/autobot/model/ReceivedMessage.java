package com.test.autobot.model;

import org.jsondoc.core.annotation.ApiObject;

/**
 * 
 * @author kalyan
 *
 */
@ApiObject
public class ReceivedMessage {

	public Entry[] getEntry() {
		return entry;
	}

	public void setEntry(Entry[] entry) {
		this.entry = entry;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	private Entry[] entry;
	private String object;

	public interface OBJECT_TYPE {
		String PAGE = "page";
	}
}
