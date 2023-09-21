package com.lepl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LePlApplication {
	public static void main(String[] args) {
		SpringApplication.run(LePlApplication.class, args);
	}

}
