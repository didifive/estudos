package com.gft.loja.dto.filial;

import com.gft.loja.dto.endereco.EnderecoMapper;
import com.gft.loja.entities.Filial;

public class FilialMapper {

	private FilialMapper() {
		throw new IllegalStateException("Utility class");

	}
	
	public static Filial fromDTO(RegistroFilialDTO dto) {
		return new Filial(null, dto.getNome(), EnderecoMapper.fromDTO(dto.getEndereco()));
	}
	
	public static ConsultaFilialDTO fromEntity(Filial filial) {
		return new ConsultaFilialDTO(filial.getId(), filial.getNome()
									, EnderecoMapper.fromEntity(filial.getEndereco()));
	}
	

}
