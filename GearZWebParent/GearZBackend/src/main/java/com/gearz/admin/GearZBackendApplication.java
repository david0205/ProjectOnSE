package com.gearz.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.gearz.common.entity", "com.gearz.admin.user" })
public class GearZBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GearZBackendApplication.class, args);
		System.out.println(System.getProperty("user.dir"));
	}

}
