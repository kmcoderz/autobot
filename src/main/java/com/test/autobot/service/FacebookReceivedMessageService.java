package com.test.autobot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.autobot.dao.FacebookReceivedMessageRepository;
import com.test.autobot.model.FacebookReceivedMessage;

@Service
public class FacebookReceivedMessageService {

	public Boolean saveMessage(String message, Long senderId, Long recipientId, Date messageCreatedAt) {
		if (message != null && senderId != null && recipientId != null) {
			FacebookReceivedMessage receivedMessage = new FacebookReceivedMessage();
			Date createdAt = new Date();
			try {
				receivedMessage.setMessage(message);
				receivedMessage.setRecipientId(recipientId);
				receivedMessage.setSenderId(senderId);
				receivedMessage.setCreatedAt(createdAt);
				receivedMessage.setMessageCreatedAt(messageCreatedAt);
				facebookReceivedMessageRepository.save(receivedMessage);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Autowired
	FacebookReceivedMessageRepository facebookReceivedMessageRepository;
}
