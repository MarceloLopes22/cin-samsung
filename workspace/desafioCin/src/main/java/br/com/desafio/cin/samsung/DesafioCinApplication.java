package br.com.desafio.cin.samsung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "br.com.desafio.cin.samsung.*")
public class DesafioCinApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DesafioCinApplication.class, args);
	}

}
