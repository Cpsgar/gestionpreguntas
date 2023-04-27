package es.mdfe.gestionpreguntas.REST.models;

import org.springframework.hateoas.RepresentationModel;

import es.mdfe.gestionpreguntas.entidades.Usuario;

public class PreguntaPostModel extends RepresentationModel<PreguntaPostModel>  {
	private String enunciado;
	private Usuario usuario;

	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
