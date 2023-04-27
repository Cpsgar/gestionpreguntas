package es.mdfe.gestionpreguntas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdfe.gestionpreguntas.entidades.Familia;

public interface FamiliaRepositorio extends JpaRepository<Familia, Long> {

}
