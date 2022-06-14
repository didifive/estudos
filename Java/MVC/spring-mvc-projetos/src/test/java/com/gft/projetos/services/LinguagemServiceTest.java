package com.gft.projetos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.repositories.LinguagemRepository;
import com.gft.projetos.utils.LinguagemUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do LinguagemService")
class LinguagemServiceTest {
	
	@Mock
	private LinguagemRepository linguagemRepository;
	
	@InjectMocks
	private LinguagemService linguagemService;
	
	private Linguagem linguagem;
	
	
	@BeforeEach
	void setup() {
  	// given
    linguagem = LinguagemUtils.createFakeEntity();
	}
	
	private void assertLinguagem(Linguagem linguagemAtual) throws MultipleFailuresError {
		Linguagem linguagemEsperada = LinguagemUtils.createFakeEntity();
		
		assertAll(
  		"linguagem",
  		() -> assertThat(linguagemAtual).isNotNull()
  		,() -> assertEquals(linguagemEsperada.getId(),							linguagemAtual.getId())
  		,() -> assertEquals(linguagemEsperada.getNome(), 						linguagemAtual.getNome())
  		,() -> assertEquals(linguagemEsperada.getDesenvolvedores(), linguagemAtual.getDesenvolvedores())
  		,() -> assertEquals(linguagemEsperada.getProjetos(), 				linguagemAtual.getProjetos())
    );
	}
	
  @Test
  @DisplayName("1. Deve salvar Linguagem")
	void shouldSaveLinguagem() throws Exception {
  	// when
    when(linguagemRepository.save(any(Linguagem.class))).thenReturn(linguagem);

    //then
    Linguagem linguagemSalva = linguagemService.salvarLinguagem(linguagem);
    
    assertLinguagem(linguagemSalva);
	}

  @Test
  @DisplayName("2. Deve listar a(s) Linguagem(ns)")
	void shouldListLanguages() throws Exception {
    // when
    when(linguagemRepository.findAll()).thenReturn(Collections.singletonList(linguagem));

    //then
    List<Linguagem> linguagensListadas = linguagemService.listarLinguagens();
        
    assertAll(
    	"listar linguagens"
      ,() -> assertThat(linguagensListadas).isNotEmpty()
      ,() -> assertThat(linguagensListadas.get(0)).isEqualTo(linguagem)
    );
	}
  
  @Test
  @DisplayName("3. Quando vai buscar e o Id de Linguagem dado é válido, então deve retornar Linguagem")
	void whenFetchAndTheValidLinguagemIdIsGivenThenShouldReturnLinguagem() throws Exception {
  	// when
    when(linguagemRepository.findById(linguagem.getId())).thenReturn(Optional.of(linguagem));

    //then
    Linguagem linguagemBuscada = linguagemService.obterLinguagem(linguagem.getId());
    
    assertLinguagem(linguagemBuscada);
  }
    
  @Test
  @DisplayName("4. Quando um Id inexistente de Linguagem é dado, então deve lançar uma excessão")
	void whenFetchAndTheNonExistentLanguageIdIsGivenThenShouldThrowException() throws Exception {
  	// when
    when(linguagemRepository.findById(linguagem.getId())).thenReturn(Optional.empty());

    //then
    assertThrows(LinguagemNaoEncontradaException.class, () -> linguagemService.obterLinguagem(linguagem.getId()));
	}
	
  @Test
  @DisplayName("5. Quando vai excluir e o Id de Linguagem dado é válido, então deve excluir a Linguagem")
	void whenWillDeleteAndValidLinguagemIdIsGivenThenShouldDeleteLinguagem() throws Exception {
  	// when
    when(linguagemRepository.findById(linguagem.getId())).thenReturn(Optional.of(linguagem));

    //then
    linguagemService.excluirLinguagem(linguagem.getId());
    
    assertAll(
    	"linguagemRepository mock"
    	, () -> verify(linguagemRepository, times(1)).findById(linguagem.getId())
    	, () -> verify(linguagemRepository, times(1)).deleteById(linguagem.getId())
    );
  }
    
  @Test
  @DisplayName("6. Quando vai excluir e um Id inexistente de Linguagem é dado, então deve lançar uma excessão")
	void whenWillDeleteAndNonExistentLinguagemIdIsGivenThenShouldThrowException() throws Exception {
  	// when
		when(linguagemRepository.findById(linguagem.getId())).thenReturn(Optional.empty());

		//then
		assertThrows(LinguagemNaoEncontradaException.class, () -> linguagemService.excluirLinguagem(linguagem.getId()));
	}
  
	
}
