package com.gft.loja.dto.usuario;

import com.gft.loja.dto.filial.ConsultaFilialDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaUsuarioDTO {
	
	private String email;
	private String nomePerfil;
	private ConsultaFilialDTO filial;
		

}
