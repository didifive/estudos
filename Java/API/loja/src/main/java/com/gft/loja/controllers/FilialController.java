package com.gft.loja.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.loja.dto.filial.ConsultaFilialDTO;
import com.gft.loja.dto.filial.FilialMapper;
import com.gft.loja.dto.filial.RegistroFilialDTO;
import com.gft.loja.entities.Filial;
import com.gft.loja.services.FilialService;

@RestController
@RequestMapping("v1/filiais")
public class FilialController {
	
	private final FilialService filialService;
	
	public FilialController(FilialService filialService) {
		this.filialService = filialService;
	}

	@GetMapping
	public ResponseEntity<List<ConsultaFilialDTO>> buscarTodasAsFiliais(){
		
		return ResponseEntity.ok(filialService.listarTodasAsFiliais().stream()
				.map(FilialMapper::fromEntity).toList());
		
	}

	
	@PostMapping
	public ResponseEntity<ConsultaFilialDTO> salvarFilial(@RequestBody RegistroFilialDTO dto){
		
		Filial filial = filialService.salvarFilial(FilialMapper.fromDTO(dto));
		
		return ResponseEntity.ok(FilialMapper.fromEntity(filial));
		
	}
	
	@GetMapping("{id}") //localhost:8080/v1/filiais/2
	public ResponseEntity<ConsultaFilialDTO> buscarFilial(@PathVariable Long id){
		
		Filial filial;
		try {
			filial = filialService.buscarFilial(id);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(FilialMapper.fromEntity(filial));
		
	}
	
	@PutMapping("{id}") //localhost:8080/v1/filiais/2
	public ResponseEntity<ConsultaFilialDTO> atualizarFilial(@RequestBody RegistroFilialDTO dto
																							, @PathVariable Long id){
		
		Filial filial;
		try {
			filial = filialService.atualizarFilial(FilialMapper.fromDTO(dto), id);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(FilialMapper.fromEntity(filial));
		
	}
	
	
	@DeleteMapping("{id}") //localhost:8080/v1/filiais/2
	public ResponseEntity<ConsultaFilialDTO> excluirFilial(@PathVariable Long id){
		
		try {
			filialService.excluirFilial(id);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
		
	}
}
