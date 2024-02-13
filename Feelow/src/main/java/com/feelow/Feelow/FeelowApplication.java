package com.feelow.Feelow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class FeelowApplication {
	public static void main(String[] args) {

		SpringApplication.run(FeelowApplication.class, args);

	}

}

