package es.mdfe.gestionpreguntas.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdfe.gestionpreguntas.GestionpreguntasApplication;
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
	public EntityModel<Usuario> one(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).
				orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}
	
	@GetMapping
	public CollectionModel<UsuarioListaModel> all(){
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("porNombreUsuario")
	public CollectionModel<UsuarioListaModel> pedidosPorEstado(@RequestParam String nombreUsuario) {
		return listaAssembler.toCollection(
				repositorio.findUsuarioByNombreUsuario(nombreUsuario)
				);
	}
	

	@PostMapping
	public EntityModel<Usuario> add(@RequestBody UsuarioModel model) {
		Usuario usuario = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + usuario);
		return assembler.toModel(usuario);
	}
	
	@PutMapping("{id}")
	public EntityModel<Usuario> edit(@PathVariable Long id, @RequestBody UsuarioModel model) {
		Usuario usuario = repositorio.findById(id).map(usr -> {
			usr.setNombre(model.getNombre());
			usr.setNombreUsuario(model.getNombreUsuario());
			usr.setRole(model.getRole());
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado usuario " + id);
		repositorio.deleteById(id);
	}
}
