package es.mdfe.gestionpreguntas.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdfe.gestionpreguntas.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findUsuarioByUsername(String username);
}
