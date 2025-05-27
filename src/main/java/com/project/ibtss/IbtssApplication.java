package com.project.ibtss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IbtssApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbtssApplication.class, args);
	}
}
