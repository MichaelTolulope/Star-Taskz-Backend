package com.niit.StarTaskz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.niit.startaskz.repository")
@SpringBootApplication
public class StarTaskzApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarTaskzApplication.class, args);
	}

}
