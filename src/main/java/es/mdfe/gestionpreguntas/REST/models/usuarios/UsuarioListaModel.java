package es.mdfe.gestionpreguntas.REST.models.usuarios;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdfe.gestionpreguntas.entidades.Usuario.Role;

@Relation(collectionRelation = "usuarios")
public class UsuarioListaModel extends RepresentationModel<UsuarioListaModel> {

	private String nombre;
	private Role role;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}

