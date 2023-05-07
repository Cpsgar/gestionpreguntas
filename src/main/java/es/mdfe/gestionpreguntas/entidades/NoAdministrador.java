package es.mdfe.gestionpreguntas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("N")
public class NoAdministrador extends Usuario {
	public static enum Departamento {
		EMIES, CCESP
	}

	public static enum Tipo {
		alumno, docente, administracion
	}
	@NotNull(message="departamento es un campo obligatorio de la clase usuario no admin")
	private Departamento departamento;
	@NotNull(message="tipo es un campo obligatorio de la clase usuario no admin")
	private Tipo tipo;

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override

	public Role getRole() {
		// TODO Auto-generated method stub
		return Role.NoAdministrador;
	}

}
