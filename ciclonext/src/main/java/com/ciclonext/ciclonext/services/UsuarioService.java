package com.ciclonext.ciclonext.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciclonext.ciclonext.dtos.UsuarioDTO;
import com.ciclonext.ciclonext.model.Grupo;
import com.ciclonext.ciclonext.model.Usuario;
import com.ciclonext.ciclonext.repository.GrupoRepository;
import com.ciclonext.ciclonext.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repositoryU;
	@Autowired
	private GrupoRepository repositoryG;
	@Autowired
	private GrupoService service;

	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {

		return repositoryU.findByEmail(novoUsuario.getEmail()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			return Optional.ofNullable(repositoryU.save(novoUsuario));
		});
	}

	public Optional<Usuario> atualizarUsuario(Long idUsuario, UsuarioDTO usuarioParaAtualizar) {
		return repositoryU.findById(idUsuario).map(usuarioExistente -> {
			usuarioExistente.setNome(usuarioParaAtualizar.getNome());
			usuarioExistente.setSenha(usuarioParaAtualizar.getSenha());
			return Optional.ofNullable(repositoryU.save(usuarioExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	// metodo para um usuário criar um grupo
	public Optional<Grupo> criarGrupo(Long idUsuario, Grupo grupoParaSerCriado) {
		Optional<Object> grupoJaExistente = service.verificarGrupo(grupoParaSerCriado);

		if (grupoJaExistente.isPresent()) {
			return repositoryU.findById(idUsuario).map(usuarioExistente -> {
				grupoParaSerCriado.setCriador(usuarioExistente);
				return Optional.ofNullable(repositoryG.save(grupoParaSerCriado));
			}).orElseGet(() -> {
				return Optional.empty();
			});
			
		} else {
			return Optional.empty();
			
		}

	}

}
