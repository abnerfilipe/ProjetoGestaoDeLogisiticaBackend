package br.com.assistecnologia.GestaoDeLogisitica;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestaoDeLogisiticaApplication {
	public static void main(String[] args) {
		SpringApplication.run(GestaoDeLogisiticaApplication.class, args);
	}
	@Bean
	InitializingBean sendDatabase() {

		return null;
	}
}
