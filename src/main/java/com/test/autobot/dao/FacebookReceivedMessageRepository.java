package com.test.autobot.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.autobot.model.FacebookReceivedMessage;

public interface FacebookReceivedMessageRepository extends MongoRepository<FacebookReceivedMessage, String> {

}
