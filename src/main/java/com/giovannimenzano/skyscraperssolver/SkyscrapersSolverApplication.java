package com.giovannimenzano.skyscraperssolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.giovannimenzano.skyscraperssolver")
public class SkyscrapersSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyscrapersSolverApplication.class, args);
	}

}
