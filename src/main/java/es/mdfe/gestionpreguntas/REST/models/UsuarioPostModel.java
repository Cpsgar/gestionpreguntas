package es.mdfe.gestionpreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdfe.gestionpreguntas.entidades.NoAdministrador.Departamento;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador.Tipo;
import es.mdfe.gestionpreguntas.entidades.Usuario.Role;

@Relation(itemRelation = "usuario")
public class UsuarioPostModel extends RepresentationModel<UsuarioPostModel> {
	private String nombre;
	private String nombreUsuario;
	private String contraseña;
	private Departamento departamento;
	private Tipo tipo;
	private String telefono;
	private Role role;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public String toString() {
		return "UsuarioPostModel [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", contraseña=" + contraseña
				+ ", role=" + role + "]";
	}
	
}
