package com.tourapi.mandi;

import org.springframework.boot.SpringApplication;

public class TestMandiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MandiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
