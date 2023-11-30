package com.feelow.Feelow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FeelowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeelowApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigure(){
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowOriginsPatterns();
//			}
//
//		};
//	}
}
