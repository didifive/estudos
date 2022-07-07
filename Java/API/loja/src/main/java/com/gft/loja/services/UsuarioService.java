package com.gft.loja.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.loja.entities.Usuario;
import com.gft.loja.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		Optional<Usuario> optional = usuarioRepository.findByEmail(email);
		
		return optional.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarUsuarioPorEmail(username);
	}

	public Usuario buscarUsuarioPorId(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		
		return usuarioRepository.save(usuario);
		
	}

}
