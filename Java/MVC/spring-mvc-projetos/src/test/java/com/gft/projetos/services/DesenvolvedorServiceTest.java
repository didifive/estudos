package com.gft.projetos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.repositories.DesenvolvedorRepository;
import com.gft.projetos.utils.DesenvolvedorUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do DesenvolvedorService")
class DesenvolvedorServiceTest {
	
	@Mock
	private DesenvolvedorRepository desenvolvedorRepository;
	
	@InjectMocks
	private DesenvolvedorService desenvolvedorService;
	
	private Desenvolvedor desenvolvedor;
	
	@BeforeEach
	void setup() {
  	// given
    desenvolvedor = DesenvolvedorUtils.createFakeEntity();
	}
	
	private void assertDesenvolvedor(Desenvolvedor desenvolvedorSalvo) throws MultipleFailuresError {
		// given
		Desenvolvedor desenvolvedorEsperado = DesenvolvedorUtils.createFakeEntity();
		
		assertAll(
  		"desenvolvedor"
			,() -> assertThat(desenvolvedorSalvo).isNotNull()
  		,() -> assertEquals(desenvolvedorEsperado.getId(),						desenvolvedorSalvo.getId())
  		,() -> assertEquals(desenvolvedorEsperado.getNome(), 					desenvolvedorSalvo.getNome())
  		,() -> assertEquals(desenvolvedorEsperado.getQuatroLetras(), 	desenvolvedorSalvo.getQuatroLetras())
  		,() -> assertEquals(desenvolvedorEsperado.getEmail(), 				desenvolvedorSalvo.getEmail())
  		,() -> assertEquals(desenvolvedorEsperado.getSalarioMensal(), desenvolvedorSalvo.getSalarioMensal())
  		,() -> assertEquals(desenvolvedorEsperado.getLinguagem(), 		desenvolvedorSalvo.getLinguagem())
  		,() -> assertEquals(desenvolvedorEsperado.getProjetos(), 			desenvolvedorSalvo.getProjetos())
    );
	}
	
	private void assertListaDesenvolvedor(List<Desenvolvedor> desenvolvedoresListados) {
		assertAll(
  		"lista de desenvolvedores"
  		,() -> assertThat(desenvolvedoresListados).isNotEmpty()
			,() -> assertThat(desenvolvedoresListados.get(0)).isEqualTo(desenvolvedor)
    );
	}
	
  @Test
  @DisplayName("1. Deve salvar Desenvolvedor")
	void shouldSaveDesenvolvedor() throws Exception {
    // when
    when(desenvolvedorRepository.save(desenvolvedor)).thenReturn(desenvolvedor);

    //then
    Desenvolvedor desenvolvedorSalvo = desenvolvedorService.salvarDesenvolvedor(desenvolvedor);
    
    assertDesenvolvedor(desenvolvedorSalvo);
	}

  @Test
  @DisplayName("2. Deve listar todos Desenvolvedores")
	void shouldListAllDesenvolvedores() throws Exception {
    // when
    when(desenvolvedorRepository.findAll()).thenReturn(Collections.singletonList(desenvolvedor));

    //then
    List<Desenvolvedor> desenvolvedoresListados = desenvolvedorService.listarDesenvolvedores(null,null);
        
    assertListaDesenvolvedor(desenvolvedoresListados);
	}
  
  @Test
  @DisplayName("2.1. Deve listar Desenvolvedores por Nome e Quatro Letras")
	void shouldListDesenvolvedoresByNomeAndQuatroLetras() throws Exception {
    // when
    when(desenvolvedorRepository.findByNomeContainsAndQuatroLetrasContains(desenvolvedor.getNome(),desenvolvedor.getQuatroLetras()))
    							.thenReturn(Collections.singletonList(desenvolvedor)
    );

    //then
    List<Desenvolvedor> desenvolvedoresListados = desenvolvedorService.listarDesenvolvedores(
    																							desenvolvedor.getNome(),desenvolvedor.getQuatroLetras());
        
    assertListaDesenvolvedor(desenvolvedoresListados);
	}
  
  @Test
  @DisplayName("2.2. Deve listar Desenvolvedores somente por Nome")
	void shouldListDesenvolvedoresOnlyByNome() throws Exception {
  	// when
    when(desenvolvedorRepository.findByNomeContainsAndQuatroLetrasContains(desenvolvedor.getNome(),null))
    							.thenReturn(Collections.singletonList(desenvolvedor)
    );

    //then
    List<Desenvolvedor> desenvolvedoresListados = desenvolvedorService.listarDesenvolvedores(
    																							desenvolvedor.getNome(),null);
        
    assertListaDesenvolvedor(desenvolvedoresListados);
	}
  
  @Test
  @DisplayName("2.3. Deve listar Desenvolvedores somente por QuatroLetras")
	void shouldListDesenvolvedoresOnlyByQuatroLetras() throws Exception {
  	// when
    when(desenvolvedorRepository.findByNomeContainsAndQuatroLetrasContains(null,desenvolvedor.getQuatroLetras()))
    							.thenReturn(Collections.singletonList(desenvolvedor)
    );

    //then
    List<Desenvolvedor> desenvolvedoresListados = desenvolvedorService.listarDesenvolvedores(
    																							null,desenvolvedor.getQuatroLetras());
        
    assertListaDesenvolvedor(desenvolvedoresListados);
	}
  
  @Test
  @DisplayName("3. Quando vai buscar e o Id de Desenvolvedor dado é válido, então deve retornar Desenvolvedor")
	void whenFetchAndTheValidDesenvolvedorIdIsGivenThenShouldReturnDesenvolvedor() throws Exception {
  	// when
    when(desenvolvedorRepository.findById(desenvolvedor.getId())).thenReturn(Optional.of(desenvolvedor));

    //then
    Desenvolvedor desenvolvedorBuscado = desenvolvedorService.obterDesenvolvedor(desenvolvedor.getId());
    
    assertDesenvolvedor(desenvolvedorBuscado);
  }
    
  @Test
  @DisplayName("4. Quando um Id inexistente de Desenvolvedor é dado, então deve lançar uma excessão")
	void whenFetchAndTheNonExistentDesenvolvedorIdIsGivenThenShouldThrowException() throws Exception {
    	// when
      when(desenvolvedorRepository.findById(desenvolvedor.getId())).thenReturn(Optional.empty());

      //then
      assertThrows(DesenvolvedorNaoEncontradoException.class, () -> desenvolvedorService.obterDesenvolvedor(desenvolvedor.getId()));
	}
	
  @Test
  @DisplayName("5. Quando vai excluir e o Id de Desenvolvedor dado é válido, então deve excluir o Desenvolvedor")
	void whenWillDeleteAndValidDesenvolvedorIdIsGivenThenShouldDeleteDesenvolvedor() throws Exception {
  	// when
    when(desenvolvedorRepository.findById(desenvolvedor.getId())).thenReturn(Optional.of(desenvolvedor));

    //then
    desenvolvedorService.excluirDesenvolvedor(desenvolvedor.getId());
    
    verify(desenvolvedorRepository, times(1)).findById(desenvolvedor.getId());
    verify(desenvolvedorRepository, times(1)).deleteById(desenvolvedor.getId());
  }
    
  @Test
  @DisplayName("6. Quando vai excluir e um Id inexistente de Desenvolvedor é dado, então deve lançar uma excessão")
	void whenWillDeleteAndNonExistentDesenvolvedorIdIsGivenThenShouldThrowException() throws Exception {
  	// when
		when(desenvolvedorRepository.findById(desenvolvedor.getId())).thenReturn(Optional.empty());

		//then
		assertThrows(DesenvolvedorNaoEncontradoException.class, () -> desenvolvedorService.excluirDesenvolvedor(desenvolvedor.getId()));
	}
	
}
