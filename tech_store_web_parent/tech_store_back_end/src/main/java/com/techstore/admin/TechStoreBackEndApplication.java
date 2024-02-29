package com.techstore.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.techstore.common.entities", "com.techstore.admin.user" })
public class TechStoreBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechStoreBackEndApplication.class, args);
	}

}
