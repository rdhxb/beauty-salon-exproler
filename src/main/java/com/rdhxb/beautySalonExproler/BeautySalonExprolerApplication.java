package com.rdhxb.beautySalonExproler;

import com.rdhxb.beautySalonExproler.dataCollector.DataCollector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeautySalonExprolerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeautySalonExprolerApplication.class, args);
	}

	@Bean
	CommandLineRunner runDataCollector(DataCollector dataCollector) {
		return args -> dataCollector.collectAndSave();
	}
}