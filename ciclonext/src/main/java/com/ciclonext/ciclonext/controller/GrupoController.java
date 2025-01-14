package com.ciclonext.ciclonext.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciclonext.ciclonext.model.Grupo;
import com.ciclonext.ciclonext.model.util.Categoria;
import com.ciclonext.ciclonext.repository.GrupoRepository;
import com.ciclonext.ciclonext.services.GrupoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/grupo")
public class GrupoController {

	
	private @Autowired GrupoRepository repositoryG;

	
	private @Autowired GrupoService service;

	@GetMapping("/getAll") // Método para pegar tudo
	public ResponseEntity<List<Grupo>> findAll() {

		return ResponseEntity.ok(repositoryG.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Grupo> findById(@PathVariable long id) {

		return repositoryG.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Object> postGrupo(@Valid @RequestBody Grupo novoGrupo) {

		Optional<Object> cadastrarGrupo = service.cadastrarGrupo(novoGrupo);

		if (cadastrarGrupo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grupo já existente, tente outro nome.");

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("Grupo criado.");

		}

	}

	@PutMapping("/{id}/atualizar")
	public ResponseEntity<Grupo> putGrupo(@Valid @PathVariable(value = "id") Long id,
			@Valid @RequestBody Grupo grupoParaAtualizar) {

		return service.atualizarGrupo(id, grupoParaAtualizar)
				.map(grupoAtualizado -> ResponseEntity.ok().body(grupoAtualizado))
				.orElse(ResponseEntity.badRequest().build());

	}

	@DeleteMapping("/{id}")
	public void deletarGrupo(@PathVariable long id) {

		repositoryG.deleteById(id);

	}

	@GetMapping("/categoria/{categoria}")
	public ResponseEntity<List<Grupo>> encontrarPorCategoria(@PathVariable Categoria categoria) {

		return ResponseEntity.ok().body(repositoryG.findAllByCategoria(categoria));

	}

	@GetMapping("/nome/{nomeGrupo}")
	public ResponseEntity<List<Grupo>> encontrarPorNomeGrupo(@PathVariable String nomeGrupo) {
		return ResponseEntity.ok().body(repositoryG.findAllByNomeGrupoContainingIgnoreCase(nomeGrupo));
	}

}