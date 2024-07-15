package com.niit.StarTaskz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@ComponentScan(basePackages = "com.niit.StarTaskz")
@EnableMongoRepositories(basePackages = "com.niit.StarTaskz.repository")
@SpringBootApplication
public class StarTaskzApplication {
    private static final Logger logger = LoggerFactory.getLogger(StarTaskzApplication.class);

	public static void main(String[] args) {
        System.setProperty("org.mongodb.driver.logging.level", "DEBUG");
		SpringApplication.run(StarTaskzApplication.class, args);
	}

}
