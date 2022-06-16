package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoNoProjetoException;
import com.gft.projetos.exceptions.ProjetoNaoEncontradoException;
import com.gft.projetos.repositories.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	public Projeto salvarProjeto(Projeto projeto) {
		
		return projetoRepository.save(projeto);
		
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
		
		return verificaProjeto(id);
		
	}

	public void excluirProjeto(Long id) throws ProjetoNaoEncontradoException {
		
		verificaProjeto(id);

		projetoRepository.deleteById(id);
		
	}
	
	public void retirarDesenvolvedorDoProjeto(Long idProjeto, Long idDesenvolvedor)
			throws ProjetoNaoEncontradoException
				, DesenvolvedorNaoEncontradoException
				, DesenvolvedorNaoEncontradoNoProjetoException
	{
		
		Projeto projeto = verificaProjeto(idProjeto);
		Desenvolvedor desenvolvedor = desenvolvedorService.verificaDesenvolvedor(idDesenvolvedor);
		
		if(!projeto.getDesenvolvedores().contains(desenvolvedor)) {
			throw new DesenvolvedorNaoEncontradoNoProjetoException("Desenvolvedor não foi encontrado no projeto.");
		} 
		
		projeto.getDesenvolvedores().remove(desenvolvedor);
		projetoRepository.save(projeto);
		
	}
	
	private Projeto verificaProjeto(Long id) throws ProjetoNaoEncontradoException {
		Optional<Projeto> projeto = projetoRepository.findById(id);
		
		if(projeto.isEmpty()) {
			throw new ProjetoNaoEncontradoException("Projeto não encontrado.");
		}
		
		return projeto.get();
	}
	
}
