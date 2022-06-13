package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.exceptions.ProjetoNaoEncontradoException;
import com.gft.projetos.repositories.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	public Projeto salvarProjeto(Projeto projeto) {
		
		Projeto projetoSalvo = projetoRepository.save(projeto);
		return projetoSalvo;
		
	}
	
	public List<Projeto> listarProjetos(String nome, String apelido) {
		
		if(nome!=null || apelido!=null)
			return listarProjetosPorNomeEApelido(nome,apelido);

		return listarTodosProjetos();
		
	}
	
	public List<Projeto> listarProjetosPorNomeEApelido(String nome, String apelido) {
		
		return projetoRepository.findByNomeContainsAndApelidoContains(nome,apelido);

	}
	
	public List<Projeto> listarTodosProjetos() {
		
		return projetoRepository.findAll();
		
	}
	
	public Projeto obterProjeto(Long id) throws ProjetoNaoEncontradoException {
		
		Optional<Projeto> projeto = projetoRepository.findById(id);
		
		if(projeto.isEmpty()) {
			throw new ProjetoNaoEncontradoException("Projeto não encontrado.");
		}
		
		return projeto.get();
	}

	public void excluirProjeto(Long id) {

		projetoRepository.deleteById(id);
		
	}
	
	public void retirarDesenvolvedorDoProjeto(Long idProjeto, Long idDesenvolvedor)
			throws ProjetoNaoEncontradoException, DesenvolvedorNaoEncontradoException {
		
		Projeto projeto = obterProjeto(idProjeto);
		Desenvolvedor desenvolvedor = desenvolvedorService.obterDesenvolvedor(idDesenvolvedor);
		
		projeto.getDesenvolvedores().remove(desenvolvedor);
		
		projetoRepository.save(projeto);
		
	}
	
}
