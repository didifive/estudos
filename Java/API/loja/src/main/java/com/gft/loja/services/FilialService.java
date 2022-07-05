package com.gft.loja.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gft.loja.entities.Filial;
import com.gft.loja.repositories.FilialRepository;

@Service
public class FilialService {
	
	private final FilialRepository filialRepository;
	
	public FilialService(FilialRepository filialRepository) {
		this.filialRepository = filialRepository;
	}

	public Filial salvarFilial(Filial filial) {
		
		return filialRepository.save(filial);
		
	}
	
	public List<Filial> listarTodasAsFiliais() {
		
		return filialRepository.findAll();
		
	}

	public Filial buscarFilial(Long id) {
		Optional<Filial> optional = filialRepository.findById(id);
		
		return optional.orElseThrow(() -> new RuntimeException("Filial não encontrada!"));
	}
	
	public Filial atualizarFilial(Filial filial, Long id) {
		Filial filialOriginal = this.buscarFilial(id);
		
		filial.setId(filialOriginal.getId());
		
		return filialRepository.save(filial); 
	}

	public Filial excluirFilial(Long id) {
		Filial filialOriginal = this.buscarFilial(id);
		
		filialRepository.delete(filialOriginal);
		return null;
	}
}
