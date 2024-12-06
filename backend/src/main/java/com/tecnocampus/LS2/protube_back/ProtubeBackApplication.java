package com.tecnocampus.LS2.protube_back;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ProtubeBackApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("ENV_PROTUBE_STORE_DIR", dotenv.get("ENV_PROTUBE_STORE_DIR"));
		System.setProperty("ENV_PROTUBE_DB", dotenv.get("ENV_PROTUBE_DB"));
		System.setProperty("ENV_PROTUBE_DB_USER", dotenv.get("ENV_PROTUBE_DB_USER"));
		System.setProperty("ENV_PROTUBE_DB_PWD", dotenv.get("ENV_PROTUBE_DB_PWD"));

		SpringApplication.run(ProtubeBackApplication.class, args);
	}

}
