package es.mdfe.gestionpreguntas.entidades;

import es.mdfe.gestionpreguntas.entidades.Usuario.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("A")
public class Administrador extends Usuario {
	
	@NotBlank(message="telefono es un campo obligatorio de la clase usuario admin")
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Transient
	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return Role.Administrador;
	}

}
