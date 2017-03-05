package com.ignoretheextraclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoAuditing
@EnableMongoRepositories
public class IgnoreTheExtraClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgnoreTheExtraClubApplication.class, args);
	}
}
