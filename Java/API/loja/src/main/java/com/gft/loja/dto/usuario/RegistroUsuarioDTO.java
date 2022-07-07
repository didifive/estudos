package com.gft.loja.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {
	
	private String email;
	private String senha;
	private Long perfilId;
	private Long filialId;	

}
