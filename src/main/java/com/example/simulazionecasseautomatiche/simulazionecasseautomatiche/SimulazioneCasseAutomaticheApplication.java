package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SimulazioneCasseAutomaticheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulazioneCasseAutomaticheApplication.class, args);
	}

}
