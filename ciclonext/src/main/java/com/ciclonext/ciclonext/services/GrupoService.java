package com.ciclonext.ciclonext.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciclonext.ciclonext.model.Grupo;
import com.ciclonext.ciclonext.repository.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository repositoryG;
		

	public Optional<Object> cadastrarGrupo(Grupo novoGrupo) {
		return repositoryG.findByNomeGrupo(novoGrupo.getNomeGrupo()).map(GrupoExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			return Optional.ofNullable(repositoryG.save(novoGrupo));
		});
	}
	public Optional<Grupo> atualizarGrupo(Long idGrupo, Grupo grupoParaAtualizar) {
		return repositoryG.findById(idGrupo).map(GrupoExistente -> {
			GrupoExistente.setNomeGrupo(grupoParaAtualizar.getNomeGrupo());
			GrupoExistente.setCategoria(grupoParaAtualizar.getCategoria());
			GrupoExistente.setDescricao(grupoParaAtualizar.getDescricao());
			return Optional.ofNullable(repositoryG.save(GrupoExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

}
