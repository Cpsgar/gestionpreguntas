package es.mdfe.gestionpreguntas.REST.models;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdfe.gestionpreguntas.entidades.Usuario;

@Relation(itemRelation = "pregunta")
public class PreguntaModel  extends RepresentationModel<PreguntaModel> {
	private String enunciado;
	
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
}
