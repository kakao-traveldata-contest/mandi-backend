package com.tourapi.mandi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MandiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MandiApplication.class, args);
	}

}
