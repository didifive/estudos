package com.gft.projetos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.MultipleFailuresError;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoNoProjetoException;
import com.gft.projetos.exceptions.ProjetoNaoEncontradoException;
import com.gft.projetos.repositories.ProjetoRepository;
import com.gft.projetos.utils.DesenvolvedorUtils;
import com.gft.projetos.utils.ProjetoUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do ProjetoService")
class ProjetoServiceTest {
	
	@Mock
	private ProjetoRepository projetoRepository;
	
	@Mock
	private DesenvolvedorService desenvolvedorService;
	
	@InjectMocks
	private ProjetoService projetoService;
	
	private Projeto projeto;
	
	@BeforeEach
	void setup() {
  	// given
		projeto = ProjetoUtils.createFakeEntity();
	}
	
	private void assertProjeto(Projeto projetoAtual) throws MultipleFailuresError {
		// given
		Projeto projetoEsperado = ProjetoUtils.createFakeEntity();
		
		assertAll(
  		"projeto"
  		,() -> assertThat(projetoAtual).isNotNull()
			,() -> assertEquals(projetoEsperado.getId(),							projetoAtual.getId())
  		,() -> assertEquals(projetoEsperado.getNome(), 						projetoAtual.getNome())
  		,() -> assertEquals(projetoEsperado.getApelido(), 				projetoAtual.getApelido())
  		,() -> assertEquals(projetoEsperado.getDataEntrega(), 		projetoAtual.getDataEntrega())
  		,() -> assertEquals(projetoEsperado.getOrcamento(), 			projetoAtual.getOrcamento())
  		,() -> assertEquals(projetoEsperado.getLinguagem(), 			projetoAtual.getLinguagem())
  		,() -> assertEquals(projetoEsperado.getDesenvolvedores(),	projetoAtual.getDesenvolvedores())
    );
	}
	
	private void assertListaProjeto(List<Projeto> projetosListados) {
		assertAll(
  		"lista de projeto"
  		,() -> assertThat(projetosListados).isNotEmpty()
			,() -> assertThat(projetosListados.get(0)).isEqualTo(projeto)
    );
	}
	
  @Test
  @DisplayName("1. Deve salvar Projeto")
	void shouldSaveDesenvolvedor() throws Exception {
    // when
    when(projetoRepository.save(projeto)).thenReturn(projeto);

    //then
    Projeto projetoSalvo = projetoService.salvarProjeto(projeto);
    
    assertProjeto(projetoSalvo);
	}

  @Test
  @DisplayName("2. Deve listar todos Projetos")
	void shouldListAllProjetos() throws Exception {
    // when
    when(projetoRepository.findAll()).thenReturn(Collections.singletonList(projeto));

    //then
    List<Projeto> projetosListados = projetoService.listarProjetos(null,null);
        
    assertListaProjeto(projetosListados);
	}
  
  @Test
  @DisplayName("2.1. Deve listar Projetos por Nome e Apelido")
	void shouldListDesenvolvedoresByNomeAndQuatroLetras() throws Exception {
    // when
    when(projetoRepository.findByNomeContainsAndApelidoContains(projeto.getNome(),projeto.getApelido()))
    							.thenReturn(Collections.singletonList(projeto)
    );

    //then
    List<Projeto> projetosListados = projetoService.listarProjetos(projeto.getNome(),projeto.getApelido());
        
    assertListaProjeto(projetosListados);
	}
  
  @Test
  @DisplayName("2.2. Deve listar Projetos somente por Nome")
	void shouldListProjetosOnlyByNome() throws Exception {
  	// when
    when(projetoRepository.findByNomeContainsAndApelidoContains(projeto.getNome(),null))
    							.thenReturn(Collections.singletonList(projeto)
    );

    //then
    List<Projeto> projetosListados = projetoService.listarProjetos(projeto.getNome(),null);
        
    assertListaProjeto(projetosListados);
	}
  
  @Test
  @DisplayName("2.3. Deve listar Projetos somente por Apelido")
	void shouldListProjetosOnlyByApelido() throws Exception {
  	// when
    when(projetoRepository.findByNomeContainsAndApelidoContains(null,projeto.getApelido()))
    							.thenReturn(Collections.singletonList(projeto)
    );

    //then
    List<Projeto> projetosListados = projetoService.listarProjetos(null,projeto.getApelido());
        
    assertListaProjeto(projetosListados);
	}

  @Test
  @DisplayName("3. Quando vai buscar e o Id de Projeto dado é válido, então deve retornar Projeto")
	void whenFetchAndTheValidProjetoIdIsGivenThenShouldReturnProjeto() throws Exception {
  	// when
    when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));

    //then
    Projeto projetoBuscado = projetoService.obterProjeto(projeto.getId());
    
    assertProjeto(projetoBuscado);
  }
    
  @Test
  @DisplayName("4. Quando um Id inexistente de Projeto é dado, então deve lançar uma excessão")
	void whenFetchAndTheNonExistentProjetoIdIsGivenThenShouldThrowException() throws Exception {
    	// when
      when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.empty());

      //then
      assertThrows(ProjetoNaoEncontradoException.class, () -> projetoService.obterProjeto(projeto.getId()));
	}
	
  @Test
  @DisplayName("5. Quando vai excluir e o Id de Projeto dado é válido, então deve excluir o Projeto")
	void whenWillDeleteAndValidDesenvolvedorIdIsGivenThenShouldDeleteDesenvolvedor() throws Exception {
  	// when
    when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));

    //then
    projetoService.excluirProjeto(projeto.getId());
    
    assertAll(
    	"Mock de projetoRepository"
      ,() -> verify(projetoRepository, times(1)).findById(projeto.getId())
      ,() -> verify(projetoRepository, times(1)).deleteById(projeto.getId())
    );
  }
    
  @Test
  @DisplayName("6. Quando vai excluir e um Id inexistente de Projeto é dado, então deve lançar uma excessão")
	void whenWillDeleteAndNonExistentProjetoIdIsGivenThenShouldThrowException() throws Exception {
  	// when
		when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.empty());

		//then
		assertThrows(ProjetoNaoEncontradoException.class, () -> projetoService.excluirProjeto(projeto.getId()));
	}
  
  @Test
  @DisplayName("7. Deve Remover Desenvolvedor do Projeto")
	void shouldRemoveDesenvolvedorFromProjeto() throws Exception {
  	//given
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	Desenvolvedor desenvolvedor2 = DesenvolvedorUtils.createFakeEntity2();
  	List<Desenvolvedor> desenvolvedores = new ArrayList<>();
  	desenvolvedores.add(desenvolvedor);
  	desenvolvedores.add(desenvolvedor2);
  	projeto.setDesenvolvedores(desenvolvedores);
  	
  	// when
		when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));
		when(desenvolvedorService.verificaDesenvolvedor(desenvolvedor.getId())).thenReturn(desenvolvedor);
		
		//then
		projetoService.retirarDesenvolvedorDoProjeto(projeto.getId(), desenvolvedor.getId());
		
		assertAll(
  		"lista de projeto"
  		,() -> assertThat(projeto.getDesenvolvedores()).isNotEmpty()
  		,() -> assertThat(projeto.getDesenvolvedores()).contains(desenvolvedor2)
	  );

	}
  
  @Test
  @DisplayName("8. Deve lançar excessão ao tentar remover Desenvolvedor que não está no Projeto")
	void shouldThrowExceptionWhenTryToRemovingDesenvolvedorWhoIsNotInProjeto() throws Exception {
  	//given
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	
  	// when
		when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));
		when(desenvolvedorService.verificaDesenvolvedor(desenvolvedor.getId())).thenReturn(desenvolvedor);
		
		//then
		assertThrows(DesenvolvedorNaoEncontradoNoProjetoException.class, () -> projetoService.retirarDesenvolvedorDoProjeto(projeto.getId(), desenvolvedor.getId()));

	}
	
}
