package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
import es.mdfe.gestionpreguntas.REST.models.preguntas.PreguntaModel;
import es.mdfe.gestionpreguntas.REST.models.preguntas.PreguntaPostModel;
import es.mdfe.gestionpreguntas.entidades.Pregunta;

@Component
public class PreguntaAssembler implements RepresentationModelAssembler<Pregunta, PreguntaModel> {
	public final Logger log;

	public PreguntaAssembler() {
		log = GestionpreguntasApplication.log;
	}
	public PreguntaModel toModel(Pregunta entity) {
		PreguntaModel model = new PreguntaModel();
		model.setEnunciado(entity.getEnunciado());
		model.add(
				linkTo(methodOn(PreguntaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(entity.getUsuario().getId())).withRel("usuario"),
				linkTo(methodOn(PreguntaController.class).one(entity.getFamilia().getId())).withRel("familia")
				);
		return model;
	}
	
	public Pregunta toEntity(PreguntaPostModel model) {
		Pregunta pregunta = new Pregunta();
		pregunta.setEnunciado(model.getEnunciado());
		pregunta.setUsuario(model.getUsuario());
		return pregunta;
	}
}
