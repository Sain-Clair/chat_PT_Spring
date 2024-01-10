package com.chun.springpt;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SpringptApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringptApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer crosConfigure(){
		
		return new WebMvcConfigurer() {
			
			@Override//CORS 설정입니다.
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Test==================");
				registry.addMapping("/**")
//				.allowedOrigins( "http://192.168.0.30:8080/", "http://localhost:8080/")
				.allowedOrigins( "*")
				.allowedHeaders( "*")
				.allowedMethods( "*") .maxAge (3600);

			}
		};
	}

}
