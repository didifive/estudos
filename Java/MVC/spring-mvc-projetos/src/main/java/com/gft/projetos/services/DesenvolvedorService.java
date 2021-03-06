package com.gft.projetos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.repositories.DesenvolvedorRepository;

@Service
public class DesenvolvedorService {
	
	public static final String ERROR_MESSAGE_NAO_ENCONTRADO = "Desenvolvedor não encontrado.";
	
	@Autowired
	private DesenvolvedorRepository desenvolvedorRepository;
	
	public Desenvolvedor salvarDesenvolvedor(Desenvolvedor desenvolvedor) {
		return desenvolvedorRepository.save(desenvolvedor);
		
	}
	
	public List<Desenvolvedor> listarDesenvolvedores(String nome, String quatroLetras) {
		
		if(nome!=null || quatroLetras!=null)
			return listarDesenvolvedoresPorNomeEQuatroLetras(nome,quatroLetras);

		return listarTodosDesenvolvedores();
	
	}
	
	public List<Desenvolvedor> listarDesenvolvedoresPorNomeEQuatroLetras(String nome, String quatroLetras) {
		
		return desenvolvedorRepository.findByNomeContainsAndQuatroLetrasContains(nome, quatroLetras);
	
	}
	
	public List<Desenvolvedor> listarTodosDesenvolvedores() {
		
		return desenvolvedorRepository.findAll();
	
	}
	
	public Desenvolvedor obterDesenvolvedor(Long id) throws DesenvolvedorNaoEncontradoException {
		
		return verificaDesenvolvedor(id);
		
	}

	public void excluirDesenvolvedor(Long id) throws DesenvolvedorNaoEncontradoException {
		
		verificaDesenvolvedor(id);

		desenvolvedorRepository.deleteById(id);
		
	}
	
	public Desenvolvedor verificaDesenvolvedor(Long id) throws DesenvolvedorNaoEncontradoException {
		Optional<Desenvolvedor> desenvolvedor = desenvolvedorRepository.findById(id);
		
		if(desenvolvedor.isEmpty()) {
			throw new DesenvolvedorNaoEncontradoException(ERROR_MESSAGE_NAO_ENCONTRADO);
		}
		
		return desenvolvedor.get();
	}

}
