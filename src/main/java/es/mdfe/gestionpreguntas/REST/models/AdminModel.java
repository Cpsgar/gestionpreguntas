package es.mdfe.gestionpreguntas.REST.models;

public class AdminModel extends UsuarioModel {
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
