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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
import es.mdfe.gestionpreguntas.REST.models.familias.FamiliaListaModel;
import es.mdfe.gestionpreguntas.REST.models.familias.FamiliaModel;
import es.mdfe.gestionpreguntas.REST.models.familias.FamiliaPostModel;
import es.mdfe.gestionpreguntas.REST.models.preguntas.PreguntaListaModel;
import es.mdfe.gestionpreguntas.REST.models.usuarios.UsuarioListaModel;
import es.mdfe.gestionpreguntas.entidades.Familia;
import es.mdfe.gestionpreguntas.entidades.Pregunta;
import es.mdfe.gestionpreguntas.entidades.Usuario;
import es.mdfe.gestionpreguntas.repositorios.FamiliaRepositorio;
import es.mdfe.gestionpreguntas.repositorios.PreguntaRepositorio;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/familias")
public class FamiliaController {
	private final FamiliaRepositorio repositorio;
	private final PreguntaRepositorio respositorioPreguntas;
	private final FamiliaAssembler assembler;
	private final UsuarioListaAssembler usuarioListaAssembler;
	private final FamiliaListaAssembler listaAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler;
	private final Logger log;

	FamiliaController(FamiliaRepositorio repositorio, FamiliaAssembler assembler,
			FamiliaListaAssembler listaAssembler, PreguntaRepositorio repositorioPreguntas,
			PreguntaListaAssembler preguntaListaAssembler, UsuarioListaAssembler usuarioListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.respositorioPreguntas = repositorioPreguntas;
		this.preguntaListaAssembler = preguntaListaAssembler;
		this.usuarioListaAssembler = usuarioListaAssembler;
		log = GestionpreguntasApplication.log;
	}

	@GetMapping("{id}")
	public FamiliaModel one(@PathVariable Long id) {
		Familia familia = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Recuperado " + familia);
		return assembler.toModel(familia);
	}

	@GetMapping
	public CollectionModel<FamiliaListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping({"{id}/usuarios"})
	public CollectionModel<UsuarioListaModel> usuarios(@PathVariable long id){
		System.err.println("usuarios");
		List<Pregunta>preguntas = respositorioPreguntas.findPreguntaByFamiliaId(id);
		Set<Usuario>usuarios = new HashSet<>();
		for (Pregunta pregunta : preguntas) {
			System.err.println(pregunta);
			usuarios.add(pregunta.getUsuario());
		}
		System.err.println(usuarios);
		return usuarioListaAssembler.toCollection(new ArrayList<>(usuarios));
	}
	
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntas(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "familia"))
				.getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),
				linkTo(methodOn(UsuarioController.class).one(id)).slash("preguntas").withSelfRel()
				);
	}
	@PostMapping
	public FamiliaModel add(@Valid @RequestBody FamiliaPostModel model) {
		Familia nuevaFamilia = assembler.toEntity(model);
		Familia familia = repositorio.save(nuevaFamilia);
		log.info("Añadido " + familia);
		return assembler.toModel(familia);
	}

	@PutMapping("{id}")
	public FamiliaModel edit(@PathVariable Long id, @RequestBody FamiliaPostModel model) {
		Familia familia = repositorio.findById(id).map(fam -> {
			fam.setEnunciado(model.getEnunciado());
			return repositorio.save(fam);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Actualizada " + familia);
		return assembler.toModel(familia);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrada familia " + id);
		repositorio.deleteById(id);
	}
	
}