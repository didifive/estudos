package com.gft.loja.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.loja.entities.Fornecedor;
import com.gft.loja.exception.EntityNotFoundException;
import com.gft.loja.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	
	private final FornecedorRepository fornecedorRepository;
	
	public FornecedorService(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}

	public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
		
		return fornecedorRepository.save(fornecedor);
		
	}
	
	public Page<Fornecedor> listarTodosOsFornecedores(Pageable pageable) {
		
		return fornecedorRepository.findAll(pageable);
		
	}

	public Fornecedor buscarFornecedor(Long id) {
		Optional<Fornecedor> optional = fornecedorRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
	}
	
	public Fornecedor atualizarFornecedor(Fornecedor fornecedor, Long id) {
		Fornecedor fornecedorOriginal = this.buscarFornecedor(id);
		
		fornecedor.setId(fornecedorOriginal.getId());
		
		return fornecedorRepository.save(fornecedor); 
	}

	public Fornecedor excluirFornecedor(Long id) {
		Fornecedor fornecedorOriginal = this.buscarFornecedor(id);
		
		fornecedorRepository.delete(fornecedorOriginal);
		return null;
	}
}
