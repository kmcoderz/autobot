package com.test.autobot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.autobot.client.dao.SendMessage;
import com.test.autobot.model.Entry;
import com.test.autobot.model.Messaging;
import com.test.autobot.model.ReceivedMessage;

/**
 * 
 * @author kalyan
 *
 */
@Service
public class MessagingService {

	public void processReceivedMessage(ReceivedMessage receivedMessage) {
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(receivedMessage);
			logger.info("ReceivedMessage: " + json);
			logger.info("ProcessReceivedMessage");
			logger.info("Message Type : "+receivedMessage.getObject());
			if (ReceivedMessage.OBJECT_TYPE.PAGE.equalsIgnoreCase(receivedMessage.getObject())) {
				// Iterate over each entry
				for (Entry entry : receivedMessage.getEntry()) {
					logger.info("ID : " + entry.getId());
					logger.info("Time : " + entry.getTime());
					for (Messaging messaging : entry.getMessaging()) {
						if (messaging.getMessage() != null) {
							logger.info("SenderID : " + messaging.getSender().getId());
							logger.info("RecipientID : " + messaging.getRecipient().getId());
							logger.info("Timestamp : " + messaging.getTimestamp());
							logger.info("Message : " + messaging.getMessage().getText());
							receivedMessageService.saveMessage(messaging.getMessage().getText(), messaging.getSender().getId(), messaging.getRecipient().getId(), messaging.getTimestamp());
							String result = botService.botResult(messaging.getMessage().getText(), messaging.getSender().getId(), messaging.getRecipient().getId());
							if (result != null) {
								if(result.length()>=315) {
									result = result.substring(0, 315);
								}
								sendMessage(result, messaging.getSender().getId(), messaging.getRecipient().getId());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public void sendMessage(String result, Long recipientId, Long senderId) {
		logger.info("RecipientID (Main): "+ recipientId + " SenderID : "+senderId);
		logger.info("SendTextMessageResponseCode : " + sendTextMessage(result, recipientId));
		facebookSentMessageService.saveMessage(result, senderId, recipientId);
	}
	
	private HttpStatus sendTextMessage(String message, Long recipientId) {
		try {
			if (message != null && recipientId != null) {
				Map<String, Object> payload = new HashMap<String, Object>();
				Map<String, Object> recipient_payload = new HashMap<String, Object>();
				Map<String, Object> message_payload = new HashMap<String, Object>();
				message_payload.put("text", message);
				recipient_payload.put("id", recipientId);
				payload.put("recipient", recipient_payload);
				payload.put("message", message_payload);
				return sendMessage.sendMessage(payload);
			} else {
				logger.error("SendTextMessage Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HttpStatus.BAD_REQUEST;
	}
	
	private HttpStatus sendGenericMessage(String []messages, Long recipientId) {
		if (messages != null && messages.length > 0 && recipientId != null) {
			Map<String, Object> payload = new HashMap<String, Object>();
			Map<String, Object> recipient_payload = new HashMap<String, Object>();
			Map<String, Object> message_payload = new HashMap<String, Object>();
			Map<String, Object> attachment_payload = new HashMap<String, Object>();
			Map<String, Object> payload_payload = new HashMap<String, Object>();
			List<HashMap<String, Object>> elements_payload = new ArrayList<HashMap<String, Object>>();
			for(String message : messages) {
				HashMap<String, Object> element_payload = new HashMap<String, Object>();
				element_payload.put("title", message);
				element_payload.put("subtitle", null);
				element_payload.put("item_url", null);
				element_payload.put("image_url", null);
				element_payload.put("buttons", null); //Add Array of buttons
				elements_payload.add(element_payload);
			}
			
			payload_payload.put("elements", elements_payload);
			payload_payload.put("template_type", "generic");
			attachment_payload.put("payload", payload_payload);
			attachment_payload.put("type", "template");
			message_payload.put("attachment", attachment_payload);
			recipient_payload.put("id", recipientId);
			payload.put("recipient", recipient_payload);
			payload.put("message", message_payload);
			
			return sendMessage.sendMessage(payload);
		} else {
			logger.error("SendGenericMessage Error");
		}
		return HttpStatus.BAD_REQUEST;
	}

	@Autowired
	SendMessage sendMessage;

	@Autowired
	BotService botService;

	@Autowired
	FacebookReceivedMessageService receivedMessageService;
	
	@Autowired
	FacebookSentMessageService facebookSentMessageService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
