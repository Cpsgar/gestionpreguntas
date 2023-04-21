package es.mdfe.gestionpreguntas.REST.models;

import es.mdfe.gestionpreguntas.entidades.NoAdministrador.Departamento;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador.Tipo;

public class NoAdminModel extends UsuarioModel {
	private Departamento departamento;
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
}
