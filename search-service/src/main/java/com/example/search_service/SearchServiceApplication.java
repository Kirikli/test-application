package com.example.search_service;

import com.example.search_service.kafka.KafkaTopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

}
