package com.gearz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GearZFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GearZFrontendApplication.class, args);
		System.out.println(System.getProperty("user.dir"));
	}

}
