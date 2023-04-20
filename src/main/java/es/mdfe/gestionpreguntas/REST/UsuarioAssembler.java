package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdfe.gestionpreguntas.REST.models.AdminModel;
import es.mdfe.gestionpreguntas.REST.models.NoAdminModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioPostModel;
import es.mdfe.gestionpreguntas.entidades.Administrador;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador;
import es.mdfe.gestionpreguntas.entidades.Usuario;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

	@Override
	public UsuarioModel toModel(Usuario entity) {
		UsuarioModel model;
		switch (entity.getRole()) {
		case Administrador: {
			AdminModel adminModel = new AdminModel();
			adminModel.setTelefono(((Administrador) entity).getTelefono());
			model = adminModel;
		}
		case NoAdministrador:{
			NoAdminModel noAdminModel = new NoAdminModel();
			noAdminModel.setDepartamento(((NoAdministrador) entity).getDepartamento());
			noAdminModel.setTipo(((NoAdministrador) entity).getTipo());
			model = noAdminModel;
		}
		default: {
			model = new UsuarioModel();
		}
		}
		model.setNombre(entity.getNombre());
		model.setNombreUsuario(entity.getNombreUsuario());
		model.setRole(entity.getRole());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public Usuario toEntity(UsuarioPostModel model) {
		Usuario usuario;
		switch (model.getRole()) {
		case Administrador: {
			Administrador administrador = new Administrador();
			administrador.setTelefono(model.getTelefono());
			usuario = administrador;
		}
		case NoAdministrador:{
			NoAdministrador noAdministrador = new NoAdministrador();
			noAdministrador.setDepartamento(model.getDepartamento());
			noAdministrador.setTipo(model.getTipo());
			usuario = noAdministrador;
		}
		default: {
			usuario = new Usuario();
		}
		}
		usuario.setNombre(model.getNombre());
		usuario.setNombreUsuario(model.getNombreUsuario());
		usuario.setContraseña(model.getContraseña());
		return usuario;
	}
	
}
