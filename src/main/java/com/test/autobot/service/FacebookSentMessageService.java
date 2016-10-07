package com.test.autobot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.autobot.dao.FacebookReceivedMessageRepository;
import com.test.autobot.dao.FacebookSentMessageRepository;
import com.test.autobot.model.FacebookReceivedMessage;
import com.test.autobot.model.FacebookSentMessage;

@Service
public class FacebookSentMessageService {

	public Boolean saveMessage(String message, Long senderId, Long recipientId) {
		if (message != null && senderId != null && recipientId != null) {
			FacebookSentMessage sentMessage = new FacebookSentMessage();
			Date createdAt = new Date();
			try {
				sentMessage.setMessage(message);
				sentMessage.setRecipientId(recipientId);
				sentMessage.setSenderId(senderId);
				sentMessage.setCreatedAt(createdAt);
				facebookSentMessageRepository.save(sentMessage);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Autowired
	FacebookSentMessageRepository facebookSentMessageRepository;
}
