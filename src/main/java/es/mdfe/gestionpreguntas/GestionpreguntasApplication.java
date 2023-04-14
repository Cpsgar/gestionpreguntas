package es.mdfe.gestionpreguntas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionpreguntasApplication {

	public static final Logger log = LoggerFactory.getLogger(GestionpreguntasApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GestionpreguntasApplication.class, args);
	}

}
