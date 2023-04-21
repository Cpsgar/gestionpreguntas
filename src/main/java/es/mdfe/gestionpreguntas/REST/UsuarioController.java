package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionpedidos.REST.ArticuloController;
import es.mdef.gestionpedidos.REST.PedidoListaModel;
import es.mdef.gestionpedidos.REST.RegisterNotFoundException;
import es.mdef.gestionpedidos.entidades.Pedido;
import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
import es.mdfe.gestionpreguntas.REST.models.PreguntaListaModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioListaModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioPostModel;
import es.mdfe.gestionpreguntas.REST.models.UsuarioPutModel;
import es.mdfe.gestionpreguntas.entidades.Administrador;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador;
import es.mdfe.gestionpreguntas.entidades.Pregunta;
import es.mdfe.gestionpreguntas.entidades.Usuario;
import es.mdfe.gestionpreguntas.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final UsuarioAssembler assembler;
	private final UsuarioListaAssembler listaAssembler;
	private final Logger log;

	UsuarioController(UsuarioRepositorio repositorio, UsuarioAssembler assembler,
			UsuarioListaAssembler listaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		log = GestionpreguntasApplication.log;
	}

	@GetMapping("{id}")
	public UsuarioModel one(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}

	@GetMapping
	public CollectionModel<UsuarioListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@GetMapping("porNombreUsuario")
	public CollectionModel<UsuarioListaModel> usuariosporNombreUsuario(@RequestParam String nombreUsuario) {
		return listaAssembler.toCollection(repositorio.findUsuarioByNombreUsuario(nombreUsuario));
	}

	@PostMapping
	public UsuarioModel add(@RequestBody UsuarioPostModel model) {
		Usuario nuevoUsuario = assembler.toEntity(model);
		System.out.println(nuevoUsuario.getRole());
		Usuario usuario = repositorio.save(nuevoUsuario);
		log.info("Añadido " + usuario);
		return assembler.toModel(usuario);
	}

	@PutMapping("{id}")
	public UsuarioModel edit(@PathVariable Long id, @RequestBody UsuarioPutModel model) {
		Usuario usuario = repositorio.findById(id).map(usr -> {
			if (usr.getRole() == null) {
			} else {
				switch (usr.getRole()) {
				case Administrador: {
					((Administrador) usr).setTelefono(model.getTelefono());
					break;
				}
				case NoAdministrador: {
					((NoAdministrador) usr).setDepartamento(model.getDepartamento());
					((NoAdministrador) usr).setTipo(model.getTipo());
					break;
				}
				}
			}
			usr.setNombre(model.getNombre());
			usr.setNombreUsuario(model.getNombreUsuario());
			return repositorio.save(usr);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado usuario " + id);
		repositorio.deleteById(id);
	}
	
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntas(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "articulo"))
				.getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pedido -> preguntaListaAssembler.toModel(pedido)).collect(Collectors.toList()),
				linkTo(methodOn(ArticuloController.class).one(id)).slash("pedidos").withSelfRel()
				);
	}
}
