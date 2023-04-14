package es.mdfe.gestionpreguntas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdfe.gestionpreguntas.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	List<Usuario> findUsuarioByNombreUsuario(String nombreUsuario);
}
