package com.java.excelJsonUtility;

import com.java.excelJsonUtility.utility.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class ExcelJsonUtilityApplication {



	public static void main(String[] args) {
		SpringApplication.run(ExcelJsonUtilityApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner() {
		return new PreTaskCommandLineRunner();
	}

	public static class PreTaskCommandLineRunner implements CommandLineRunner {

		@Override
		public void run(String... strings) throws Exception {
			try {
				Files.createDirectories(Constants.sourceFolder);
				Files.createDirectories(Constants.generatedFileFolder);
			} catch (IOException e) {
				throw new RuntimeException("Could not create folder for upload!");
			}
		}
	}


}
