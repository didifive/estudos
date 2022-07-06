package com.gft.loja.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.loja.dto.fornecedor.ConsultaFornecedorDTO;
import com.gft.loja.dto.fornecedor.FornecedorMapper;
import com.gft.loja.dto.fornecedor.RegistroFornecedorDTO;
import com.gft.loja.entities.Fornecedor;
import com.gft.loja.services.FornecedorService;

@RestController
@RequestMapping("v1/fornecedores")
public class FornecedorController {
	
	private final FornecedorService fornecedorService;
	
	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@GetMapping
	public ResponseEntity<Page<ConsultaFornecedorDTO>> buscarTodosOsFornecedores(@PageableDefault Pageable pageable){
		
		return ResponseEntity.ok(fornecedorService.listarTodosOsFornecedores(pageable).map(FornecedorMapper::fromEntity));
		
	}

	
	@PostMapping
	public ResponseEntity<ConsultaFornecedorDTO> salvarFornecedor(@RequestBody RegistroFornecedorDTO dto){
		
		Fornecedor fornecedor = fornecedorService.salvarFornecedor(FornecedorMapper.fromDTO(dto));
		
		return ResponseEntity.ok(FornecedorMapper.fromEntity(fornecedor));
		
	}
	
	@GetMapping("{id}") //localhost:8080/v1/fornecedores/2
	public ResponseEntity<ConsultaFornecedorDTO> buscarFornecedor(@PathVariable Long id){
		
		Fornecedor fornecedor;

		fornecedor = fornecedorService.buscarFornecedor(id);
		
		return ResponseEntity.ok(FornecedorMapper.fromEntity(fornecedor));
		
	}
	
	@PutMapping("{id}") //localhost:8080/v1/fornecedores/2
	public ResponseEntity<ConsultaFornecedorDTO> atualizarFornecedor(@RequestBody RegistroFornecedorDTO dto
																							, @PathVariable Long id){
		
		Fornecedor fornecedor;
		fornecedor = fornecedorService.atualizarFornecedor(FornecedorMapper.fromDTO(dto), id);

		return ResponseEntity.ok(FornecedorMapper.fromEntity(fornecedor));
		
	}
	
	
	@DeleteMapping("{id}") //localhost:8080/v1/fornecedores/2
	public ResponseEntity<ConsultaFornecedorDTO> excluirFornecedor(@PathVariable Long id){
		
		fornecedorService.excluirFornecedor(id);
		
		return ResponseEntity.ok().build();
		
	}
}
