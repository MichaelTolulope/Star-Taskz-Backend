//package com.niit.StarTaskz.configurations;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfig {
//    @Bean
//    public MongoClient mongoClient() {
//        ConnectionString connectionString = new ConnectionString("mongodb+srv://michael:admin@mongodb+srv://michael:admin@cluster0.8hc4zox.mongodb.net/star-taskz?retryWrites=true&w=majority&appName=Cluster0/test");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .build();
//        return MongoClients.create(mongoClientSettings);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "star-taskz");
//    }
//}
