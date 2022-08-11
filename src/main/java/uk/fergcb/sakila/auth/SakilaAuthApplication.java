package uk.fergcb.sakila.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakilaAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SakilaAuthApplication.class, args);
	}

}
