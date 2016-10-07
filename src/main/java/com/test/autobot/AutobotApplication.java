package com.test.autobot;

import org.alicebot.ab.Bot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@Configuration 
@EnableAutoConfiguration 
public class AutobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutobotApplication.class, args);
	}

	@Bean
	public Bot createBot() {
		Bot bot = new Bot(botName, System.getProperty("user.dir"), botType);
		return bot;
	}
	
//	@Bean
//	public Chat createBot() {
//		Bot bot = new Bot(botName, System.getProperty("user.dir"), botType);
//		System.out.println("BotMain:"+bot);
//		return new Chat(bot);
//	}
	

	@Value("${bot.name}")
	private String botName;

	@Value("${bot.type}")
	private String botType;
}
