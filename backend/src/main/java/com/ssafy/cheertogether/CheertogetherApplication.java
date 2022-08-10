package com.ssafy.cheertogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CheertogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheertogetherApplication.class, args);
	}

}
