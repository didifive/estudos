package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.repositories.DesenvolvedorRepository;
import com.gft.projetos.repositories.LinguagemRepository;

@Service
public class DesenvolvedorService {
	
	@Autowired
	private DesenvolvedorRepository desenvolvedorRepository;
	
	@Autowired
	private LinguagemRepository linguagemRepository;
	
	public Desenvolvedor salvarDesenvolvedor(Desenvolvedor desenvolvedor) {
		return desenvolvedorRepository.save(desenvolvedor);
		
	}
	
	public List<Desenvolvedor> listarDesenvolvedores() {
		
		return desenvolvedorRepository.findAll();
	}
	
	public Desenvolvedor obterDesenvolvedor(Long id) throws Exception {
		
		Optional<Desenvolvedor> desenvolvedor = desenvolvedorRepository.findById(id);
		
		if(desenvolvedor.isEmpty()) {
			throw new Exception("Desenvolvedor não encontrado.");
		}
		
		return desenvolvedor.get();
	}

	public void excluirDesenvolvedor(Long id) {

		desenvolvedorRepository.deleteById(id);
		
	}

}
