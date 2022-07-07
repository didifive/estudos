package com.gft.loja.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gft.loja.dto.auth.AutenticacaoDTO;
import com.gft.loja.dto.auth.TokenDTO;
import com.gft.loja.entities.Usuario;

@Service
public class AutenticacaoService {
	
	private final AuthenticationManager authManager;
	
	@Value("${loja.jwt.expiration}")
	private String expiration;
	
	@Value("${loja.jwt.secret}")
	private String secret;
	
	@Value("${loja.jwt.issuer}")
	private String issuer;
	
	public AutenticacaoService(@Lazy AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	public TokenDTO autenticarDto(AutenticacaoDTO authDTO) throws AuthenticationException {
		
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getSenha()));
		
		String token = gerarToken(authenticate);
		
		return new TokenDTO(token);
		
	}
	
	private Algorithm criarAlgoritimo() {
		
		return Algorithm.HMAC256(secret);
		
	}

	private String gerarToken(Authentication authenticate) {
		
		Usuario principal = (Usuario)authenticate.getPrincipal();
		
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(dataExpiracao)
				.withSubject(principal.getId().toString())
				.sign(this.criarAlgoritimo());
		
	}
	
	public boolean verificaToken(String token) {
		
		if(token==null)
			return false;
		
		try {
			
			JWT.require(this.criarAlgoritimo()).withIssuer(issuer).build().verify(token);
			
			return true;
			
		} catch (JWTVerificationException e) {

			return false;
			
		}
		
	}
	
	public Long retornarIdUsuario(String token) {
		
		String subject = JWT.require(this.criarAlgoritimo()).withIssuer(issuer).build().verify(token).getSubject();
		
		return Long.parseLong(subject);
	}

}
