package com.gft.loja.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.loja.entities.Filial;
import com.gft.loja.exception.EntityNotFoundException;
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
	
	public Page<Filial> listarTodasAsFiliais(Pageable pageable) {
		
		return filialRepository.findAll(pageable);
		
	}

	public Filial buscarFilial(Long id) {
		Optional<Filial> optional = filialRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Filial não encontrada"));
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
