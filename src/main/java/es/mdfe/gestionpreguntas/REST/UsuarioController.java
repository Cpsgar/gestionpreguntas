package es.mdfe.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
import es.mdfe.gestionpreguntas.REST.models.familias.FamiliaListaModel;
import es.mdfe.gestionpreguntas.REST.models.preguntas.PreguntaListaModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioListaModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioPostModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioPutModel;
import es.mdfe.gestionpreguntas.entidades.Administrador;
import es.mdfe.gestionpreguntas.entidades.Familia;
import es.mdfe.gestionpreguntas.entidades.NoAdministrador;
import es.mdfe.gestionpreguntas.entidades.Pregunta;
import es.mdfe.gestionpreguntas.entidades.Usuario;
import es.mdfe.gestionpreguntas.repositorios.PreguntaRepositorio;
import es.mdfe.gestionpreguntas.repositorios.UsuarioRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final PreguntaRepositorio respositorioPreguntas;
	private final UsuarioAssembler assembler;
	private final UsuarioListaAssembler listaAssembler;
	private final Logger log;
	private final FamiliaListaAssembler familiaListaAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler;

	UsuarioController(UsuarioRepositorio repositorio, PreguntaRepositorio respositorioPreguntas,
			UsuarioAssembler assembler, UsuarioListaAssembler listaAssembler, 
			FamiliaListaAssembler familiaListaAssembler, PreguntaListaAssembler preguntaListaAssembler) {
		this.repositorio = repositorio;
		this.respositorioPreguntas = respositorioPreguntas;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		log = GestionpreguntasApplication.log;
		this.familiaListaAssembler =familiaListaAssembler;
		this.preguntaListaAssembler = preguntaListaAssembler;
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


	@PostMapping
	public UsuarioModel add(@Valid @RequestBody UsuarioPostModel model) {
		Usuario nuevoUsuario = assembler.toEntity(model);
		//System.out.println(nuevoUsuario.getRole());
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
			usr.setUsername(model.getUsername());
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
	
	@PatchMapping("{id}/cambiarContrasena")
	public UsuarioModel edit(@PathVariable Long id, @RequestBody String newPassword) {
		Usuario nuevoUsuario = repositorio.findById(id).map(usr -> {
			usr.setPassword(new BCryptPasswordEncoder().encode(newPassword));
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "empleado"));
		log.info("Actualizado " + nuevoUsuario);
		return assembler.toModel(nuevoUsuario);
	}
	
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntas(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"))
				.getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),
				linkTo(methodOn(UsuarioController.class).one(id)).slash("preguntas").withSelfRel()
				);
	}


	@GetMapping({"{id}/familias"})
	public CollectionModel<FamiliaListaModel> familias(@PathVariable long id){
		List<Pregunta>preguntas = respositorioPreguntas.findPreguntaByUsuario(id);
		Set<Familia>familias = new HashSet<>();
		for (Pregunta pregunta : preguntas) {
			familias.add(pregunta.getFamilia());
		}
		return familiaListaAssembler.toCollection(new ArrayList<>(familias));
	}
}
