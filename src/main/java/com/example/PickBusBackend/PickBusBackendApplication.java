package com.example.PickBusBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PickBusBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickBusBackendApplication.class, args);
	}

}
