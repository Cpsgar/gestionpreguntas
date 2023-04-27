package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
import es.mdfe.gestionpreguntas.REST.models.usuarios.AdminModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.NoAdminModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioPostModel;
import es.mdfe.gestionpreguntas.entidades.Administrador;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador;
import es.mdfe.gestionpreguntas.entidades.Usuario;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

	public final Logger log;

	public UsuarioAssembler() {
		log = GestionpreguntasApplication.log;
	}

	@Override
	public UsuarioModel toModel(Usuario entity) {
		UsuarioModel model;
		if (entity.getRole() == null) {
			model = new UsuarioModel();
		} else {
			switch (entity.getRole()) {
			case Administrador: {
				AdminModel adminModel = new AdminModel();
				adminModel.setTelefono(((Administrador) entity).getTelefono());
				model = adminModel;
				break;
			}
			case NoAdministrador: {
				NoAdminModel noAdminModel = new NoAdminModel();
				noAdminModel.setDepartamento(((NoAdministrador) entity).getDepartamento());
				noAdminModel.setTipo(((NoAdministrador) entity).getTipo());
				model = noAdminModel;
				break;
			}
			default: {
				model = new UsuarioModel();
				break;
			}
			}
		}

		model.setNombre(entity.getNombre());
		model.setNombreUsuario(entity.getNombreUsuario());
		model.setRole(entity.getRole());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).preguntas(entity.getId())).withRel("preguntas"),
				linkTo(methodOn(UsuarioController.class).familias(entity.getId())).withRel("familias")
				);
		return model;
	}

	public Usuario toEntity(UsuarioPostModel model) {
		Usuario usuario;
		if (model.getRole() == null) {
			usuario = new Usuario();
		} else {
			switch (model.getRole()) {
			case Administrador: {
				Administrador administrador = new Administrador();
				administrador.setTelefono(model.getTelefono());
				usuario = administrador;
				break;
			}
			case NoAdministrador: {
				NoAdministrador noAdministrador = new NoAdministrador();
				noAdministrador.setDepartamento(model.getDepartamento());
				noAdministrador.setTipo(model.getTipo());
				usuario = noAdministrador;
				break;
			}
			default: {
				usuario = new Usuario();
				break;
			}
			}
		}
		usuario.setNombre(model.getNombre());
		usuario.setNombreUsuario(model.getNombreUsuario());
		usuario.setContraseña(model.getContraseña());
		return usuario;
	}

}
