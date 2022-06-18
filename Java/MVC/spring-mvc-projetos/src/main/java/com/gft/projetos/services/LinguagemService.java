package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.repositories.LinguagemRepository;

@Service
public class LinguagemService {
	
	public static final String ERROR_MESSAGE_NAO_ENCONTRADO = "Linguagem não encontrada.";
	
	@Autowired
	private LinguagemRepository linguagemRepository;
	
	public Linguagem salvarLinguagem(Linguagem linguagem) {
		
		return linguagemRepository.save(linguagem);
		
	}
	
	public List<Linguagem> listarLinguagens() {
		
		return linguagemRepository.findAll();
		
	}
	
	public Linguagem obterLinguagem(Long id) throws LinguagemNaoEncontradaException {
		
		return verificaLinguagem(id);
		
	}

	public void excluirLinguagem(Long id) throws LinguagemNaoEncontradaException {
		
		verificaLinguagem(id);

		linguagemRepository.deleteById(id);
		
	}

	private Linguagem verificaLinguagem(Long id) throws LinguagemNaoEncontradaException {
		Optional<Linguagem> linguagem = linguagemRepository.findById(id);
		
		if(linguagem.isEmpty()) {
			throw new LinguagemNaoEncontradaException(ERROR_MESSAGE_NAO_ENCONTRADO);
		}
		
		return linguagem.get();
		
	}

}
