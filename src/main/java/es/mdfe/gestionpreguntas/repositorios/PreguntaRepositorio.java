package es.mdfe.gestionpreguntas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdfe.gestionpreguntas.entidades.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long> {
	List<Pregunta> findPreguntaByEnunciado(String enunciado);
}
