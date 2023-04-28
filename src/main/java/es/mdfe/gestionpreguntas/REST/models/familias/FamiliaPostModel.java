package es.mdfe.gestionpreguntas.REST.models.familias;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.constraints.NotBlank;

@Relation(itemRelation = "familia")
public class FamiliaPostModel extends RepresentationModel<FamiliaPostModel> {
	@NotBlank(message="enunciado es un campo obligatorio de la clase familia")
	private String enunciado;
	
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
}