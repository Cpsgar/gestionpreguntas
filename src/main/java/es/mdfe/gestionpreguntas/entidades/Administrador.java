package es.mdfe.gestionpreguntas.entidades;

import es.mdfe.gestionpreguntas.entidades.Usuario.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Administrador extends Usuario {
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return Role.Administrador;
	}

	@Override
	public String toString() {
		return "Administrador [telefono=" + telefono + "]";
	}

}
