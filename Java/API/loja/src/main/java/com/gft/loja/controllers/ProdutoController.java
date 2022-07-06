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

import com.gft.loja.dto.produto.ConsultaProdutoDTO;
import com.gft.loja.dto.produto.ProdutoMapper;
import com.gft.loja.dto.produto.RegistroProdutoDTO;
import com.gft.loja.entities.Produto;
import com.gft.loja.services.ProdutoService;

@RestController
@RequestMapping("v1/produtos")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping
	public ResponseEntity<Page<ConsultaProdutoDTO>> buscarTodosOsProdutos(@PageableDefault Pageable pageable){
		
		return ResponseEntity.ok(produtoService.listarTodosOsProdutos(pageable).map(ProdutoMapper::fromEntity));
		
	}

	
	@PostMapping
	public ResponseEntity<ConsultaProdutoDTO> salvarProduto(@RequestBody RegistroProdutoDTO dto){
		
		Produto produto = produtoService.salvarProduto(ProdutoMapper.fromDTO(dto));
		
		return ResponseEntity.ok(ProdutoMapper.fromEntity(produto));
		
	}
	
	@GetMapping("{id}") //localhost:8080/v1/produtos/2
	public ResponseEntity<ConsultaProdutoDTO> buscarProduto(@PathVariable Long id){
		
		Produto produto;

		produto = produtoService.buscarProduto(id);
		
		return ResponseEntity.ok(ProdutoMapper.fromEntity(produto));
		
	}
	
	@PutMapping("{id}") //localhost:8080/v1/produtos/2
	public ResponseEntity<ConsultaProdutoDTO> atualizarProduto(@RequestBody RegistroProdutoDTO dto
																							, @PathVariable Long id){
		
		Produto produto;
		produto = produtoService.atualizarProduto(ProdutoMapper.fromDTO(dto), id);

		return ResponseEntity.ok(ProdutoMapper.fromEntity(produto));
		
	}
	
	
	@DeleteMapping("{id}") //localhost:8080/v1/produtos/2
	public ResponseEntity<ConsultaProdutoDTO> excluirProduto(@PathVariable Long id){
		
		produtoService.excluirProduto(id);
		
		return ResponseEntity.ok().build();
		
	}
}
