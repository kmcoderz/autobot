package com.test.autobot.service;

import java.util.HashMap;
import java.util.Map;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author kalyan
 *
 */
@Service
public class BotService {

	public String botResult(String message, Long senderId, Long recipientId) {
		if (message != null && senderId != null) {
			Map<Integer, Object> hashCode = new HashMap<Integer, Object>();
			Chat chatSession = fetchChatSession(senderId);
			MagicBooleans.trace_mode = true;
			if (chatSession != null) {
				bot.brain.nodeStats();
				String response = chatSession.multisentenceRespond(message);
				return response.replaceAll("\\s+"," ");
			}
		}
		return null;
	}

	private Chat fetchChatSession(Long senderId) {
		Chat chatSession = null;
		try {
			chatSession = (Chat) chatCache.get("ChatSession_" + senderId);
			if (chatSession != null) {
				return chatSession;
			} else {
				chatSession = new Chat(bot);
				chatCache.put("ChatSession_" + senderId, chatSession);
				return chatSession;
			}
		} catch (Exception e) {
			logger.error("MapCache Exception : " + e.getMessage());
		}
		return null;
	}
	
	public String testMethod(String test, Integer hashCode) {
		return "Test Method";
	}
	
	
	
	@Autowired
	Bot bot;
	
	private Map<Object, Object> chatCache = new HashMap<Object, Object>();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
