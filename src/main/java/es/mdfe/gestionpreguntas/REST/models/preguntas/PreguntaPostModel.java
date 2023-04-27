package es.mdfe.gestionpreguntas.REST.models.preguntas;

import org.springframework.hateoas.RepresentationModel;

import es.mdfe.gestionpreguntas.entidades.Familia;
import es.mdfe.gestionpreguntas.entidades.Usuario;

public class PreguntaPostModel extends RepresentationModel<PreguntaPostModel>  {
	private String enunciado;
	private Usuario usuario;
	private Familia familia;

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
	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
}
