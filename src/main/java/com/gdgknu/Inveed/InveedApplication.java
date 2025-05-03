package com.gdgknu.Inveed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InveedApplication {

	public static void main(String[] args) {
		SpringApplication.run(InveedApplication.class, args);
	}

}
