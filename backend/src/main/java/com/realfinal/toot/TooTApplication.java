package com.realfinal.toot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "com.realfinal")
@EnableJpaRepositories(basePackages = "com.realfinal")
@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TooTApplication {

	public static void main(String[] args) {
		SpringApplication.run(TooTApplication.class, args);
	}

}
