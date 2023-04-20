package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdfe.gestionpreguntas.entidades.Usuario;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

	@Override
	public UsuarioModel toModel(Usuario entity) {
		UsuarioModel model = new UsuarioModel();
		model.setNombre(entity.getNombre());
		model.setNombreUsuario(entity.getNombreUsuario());
		model.setRole(entity.getRole());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public Usuario toEntity(UsuarioPostModel model) {
		Usuario usuario = new Usuario();
		usuario.setNombre(model.getNombre());
		usuario.setNombreUsuario(model.getNombreUsuario());
		usuario.setContraseña(model.getContraseña());
		usuario.setRole(model.getRole());
		return usuario;
	}
	
}
