package com.example.demo.twotomcats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TwoTomcatsApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources();
		SpringApplication.run(TwoTomcatsApplication.class, args);
	}

}
