package com.test.autobot.controller;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.autobot.model.ReceivedMessage;
import com.test.autobot.service.BotService;
import com.test.autobot.service.MessagingService;
import com.test.autobot.util.Constant;

/**
 * 
 * @author kalyan
 *
 */
@RestController
@RequestMapping(value = "/")
public class WebhookController {

	@RequestMapping(value = "webhook", method = RequestMethod.GET)
	public @ApiResponseObject ResponseEntity<?> verifyRequest(@RequestParam("hub.mode") String hubMode, @RequestParam("hub.verify_token") String hubVerifyToken,
			@RequestParam("hub.challenge") String hubChallenge) {
		logger.info("VerifyRequest");
		if (Constant.HUBMODE.equalsIgnoreCase(hubMode) && Constant.VALIDATION_TOKEN.equalsIgnoreCase(hubVerifyToken)) {
			logger.info("Validated Webhook");
			return new ResponseEntity<Object>(hubChallenge, HttpStatus.OK);
		} else {
			logger.error("Failed validation. Make sure the validation tokens match");
			return new ResponseEntity<Object>(null, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "webhook", method = RequestMethod.POST)
	public @ApiResponseObject ResponseEntity<?> receiveMessage(@RequestBody ReceivedMessage receivedMessage) throws JsonProcessingException {
		logger.info("ReceiveMessage");
		if (receivedMessage != null) {
			messagingService.processReceivedMessage(receivedMessage);
		} else {
			logger.error("No message");
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value = "bot", method = RequestMethod.POST)
	public @ApiResponseObject ResponseEntity<?> botResponse(@RequestBody String message) {
		return new ResponseEntity<Object>(botService.botResult(message, 123L, 456L), HttpStatus.OK);
	}
	
	@Autowired
	BotService botService;

	@Autowired
	MessagingService messagingService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
