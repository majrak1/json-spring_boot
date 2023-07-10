package com.marand.json_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JsonParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonParserApplication.class, args);
	}

}
