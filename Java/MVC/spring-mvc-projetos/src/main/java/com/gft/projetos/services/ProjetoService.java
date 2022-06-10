package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.repositories.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	DesenvolvedorService desenvolvedorService;
	
	public Projeto salvarProjeto(Projeto projeto) {
		
		Projeto projetoSalvo = projetoRepository.save(projeto);
		return projetoSalvo;
		
	}
	
	public List<Projeto> listarProjetos() {
		
		return projetoRepository.findAll();
	}
	
	public Projeto obterProjeto(Long id) throws Exception {
		
		Optional<Projeto> projeto = projetoRepository.findById(id);
		
		if(projeto.isEmpty()) {
			throw new Exception("Projeto não encontrado.");
		}
		
		return projeto.get();
	}

	public void excluirProjeto(Long id) {

		projetoRepository.deleteById(id);
		
	}
	
	public void retirarDesenvolvedorDoProjeto(Long idProjeto, Long idDesenvolvedor) throws Exception {
		
		Projeto projeto = obterProjeto(idProjeto);
		Desenvolvedor desenvolvedor = desenvolvedorService.obterDesenvolvedor(idDesenvolvedor);
		
		projeto.getDesenvolvedores().remove(desenvolvedor);
		
		projetoRepository.save(projeto);
	}
	
}
