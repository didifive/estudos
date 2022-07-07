package com.gft.loja.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.loja.dto.auth.AutenticacaoDTO;
import com.gft.loja.dto.auth.TokenDTO;
import com.gft.loja.services.AutenticacaoService;

@RestController
@RequestMapping("/v1/auth")
public class AutenticacaoController {
	
	private final AutenticacaoService autenticacaoService;
	
	public AutenticacaoController(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}

	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authForm) {
		
		try {
			return ResponseEntity.ok(autenticacaoService.autenticarDto(authForm));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
}
