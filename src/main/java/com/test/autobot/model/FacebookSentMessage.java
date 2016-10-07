package com.test.autobot.model;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "sent_message")
@ApiObject
public class FacebookSentMessage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	@Id
	@Field("_id")
	@JsonProperty("id")
	private String id;
	@Field("sender_id")
	@Indexed
	@JsonProperty("sender_id")
	private Long senderId;
	@Field("recipient_id")
	@JsonProperty("recipient_id")
	private Long recipientId;
	@Field("created_at")
	@JsonProperty("created_at")
	private Date createdAt;
	@Field("message")
	@JsonProperty("message")
	private String message;
}
