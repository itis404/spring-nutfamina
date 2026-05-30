package com.wwwgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WwwgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(WwwgameApplication.class, args);
	}

}
