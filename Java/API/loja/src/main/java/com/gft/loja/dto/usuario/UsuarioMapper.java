package com.gft.loja.dto.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gft.loja.dto.filial.FilialMapper;
import com.gft.loja.entities.Filial;
import com.gft.loja.entities.Perfil;
import com.gft.loja.entities.Usuario;

public class UsuarioMapper {
	
	private UsuarioMapper() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Usuario fromDTO(RegistroUsuarioDTO dto) {
		
		Perfil perfil = new Perfil();
		perfil.setId(dto.getPerfilId());
		
		Filial filial = new Filial();
		filial.setId(dto.getFilialId());
		
		return new Usuario(null, dto.getEmail(), new BCryptPasswordEncoder().encode(dto.getSenha()), perfil, filial);
		
	}
	
	public static ConsultaUsuarioDTO fromEntity(Usuario usuario) {
		
		return new ConsultaUsuarioDTO(usuario.getEmail(), usuario.getPerfil().getNome(), FilialMapper.fromEntity(usuario.getFilial()));
		
	}

}
