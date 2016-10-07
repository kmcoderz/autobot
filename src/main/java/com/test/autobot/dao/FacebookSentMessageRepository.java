package com.test.autobot.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.autobot.model.FacebookSentMessage;

public interface FacebookSentMessageRepository extends MongoRepository<FacebookSentMessage, String> {

}
