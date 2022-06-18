package com.gft.projetos.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertCompareListModelAttribute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.builders.LinguagemBuilder;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.services.LinguagemService;
import com.gft.projetos.utils.LinguagemUtils;

import static com.gft.projetos.controllers.LinguagemController.LINGUAGEM;
import static com.gft.projetos.controllers.LinguagemController.MENSAGEM;
import static com.gft.projetos.controllers.LinguagemController.LISTA;
import static com.gft.projetos.controllers.LinguagemController.MESSAGE_SAVE_SUCCESS;
import static com.gft.projetos.controllers.LinguagemController.MESSAGE_DELETE_SUCCESS;
import static com.gft.projetos.controllers.LinguagemController.MESSAGE_DELETE_ERROR;

import static com.gft.projetos.services.LinguagemService.ERROR_MESSAGE_NAO_ENCONTRADO;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do LinguagemController")
class LinguagemControllerTest {

	static final String PATH = "/linguagem";
	static final String EDITAR = PATH + "/editar";
	static final String DETALHES = PATH + "/detalhes";
	static final String EXCLUIR = PATH + "/excluir";
	
	static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
  @Mock
  private LinguagemService linguagemService;

  @InjectMocks
  private LinguagemController linguagemController;

  @BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(linguagemController).build();
  }

 
  @Test
  @DisplayName("1. Quando GET é chamado então status ok, arquivo correto e lista com linguagem são retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListLinguagemAreReturned() throws Exception {
  	//given
  	List<Linguagem> linguagemLista = Collections.singletonList(LinguagemUtils.createFakeEntity());
  	
  	//when
  	when(linguagemService.listarLinguagens()).thenReturn(linguagemLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH))
  			.andExpectAll(
  					status().isOk(),
  					view().name("linguagem/listar.html")
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Linguagens"
  		,() -> assertModelAttributeAvailable(mv, LISTA)
  		,() -> assertCompareListModelAttribute(mv, LISTA, linguagemLista)
  	);
  	
  }
  
  @Test
  @DisplayName("2. Quando GET é chamado em editar então status ok e arquivo correto são retornados")
  void whenGETIsCalledInEditarThenStatusOkAndCorrectFileAreReturned() throws Exception {

  	//then
  	mockMvc.perform(get(EDITAR))
  			.andExpectAll(
  					status().isOk(),
  					view().name("linguagem/form.html")
  			);
  	
  }
  
  	
  @Test
  @DisplayName("2.1. Quando GET é chamado em editar sem uma id então uma nova Linguagem é retornada")
  void whenGETIsCalledInEditarWithoutIdThenANewLinguagemIsReturned() throws Exception {

  	//then
  	ModelAndView mv = mockMvc.perform(get(EDITAR))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, LINGUAGEM, new Linguagem());
  
  }
  
  @Test
  @DisplayName("2.2. Quando GET é chamado em editar com id válido então uma Linguagem válida é retornada")
  void whenGETIsCalledInEditarWithValidIdThenAValidLinguagemIsReturned() throws Exception {
  	//given
  	Linguagem linguagem = LinguagemUtils.createFakeEntity();
  	String caminho = EDITAR + "?id=" + linguagem.getId();
  	
  	//when
  	when(linguagemService.obterLinguagem(linguagem.getId())).thenReturn(linguagem);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, LINGUAGEM, linguagem);
  
  }
  
  @Test
  @DisplayName("2.3. Quando GET é chamado em editar com id inválido então uma nova Linguagem e mensagem são retornados")
  void whenGETIsCalledInEditarWithInvalidIdThenANewLinguagemAndMensagemAreReturned() throws Exception {
  	//given
  	String caminho = EDITAR + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(linguagemService.obterLinguagem(ID_INVALIDO)).thenThrow(LinguagemNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se é linguagem nova e existe mensagem"
  		,() ->	assertModelAttributeValue(mv, LINGUAGEM, new Linguagem())
  		,() ->	assertModelAttributeAvailable(mv, MENSAGEM)
  	);
  			
  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhes com id válido então uma Linguagem válida é retornada")
  void whenGETIsCalledInDetalhesWithValidIdThenAValidLinguagemIsReturned() throws Exception {
  	//given
  	Linguagem linguagem = LinguagemUtils.createFakeEntity();
  	String caminho = DETALHES + "?id=" + linguagem.getId();
  	
  	//when
  	when(linguagemService.obterLinguagem(linguagem.getId())).thenReturn(linguagem);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name("linguagem/detalhes.html")
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, LINGUAGEM, linguagem);
  
  }
  
  @Test
  @DisplayName("3.1. Quando GET é chamado em detalhes com id inválido então mensagem é retornada")
  void whenGETIsCalledInDetalhesWithInvalidIdThenMensagemIsReturned() throws Exception {
  	//given
  	String caminho = DETALHES + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(linguagemService.obterLinguagem(ID_INVALIDO)).thenThrow(LinguagemNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeAvailable(mv, MENSAGEM);
  			
  }
  
  @Test
  @DisplayName("4. Quando GET é chamado em excluir com id válido então uma mensagem de sucesso é retornada")
  void whenGETIsCalledInExcluirWithValidIdThenASucessMensagemIsReturned() throws Exception {
  	//given
  	Linguagem linguagem = LinguagemUtils.createFakeEntity();
  	String caminho = EXCLUIR + "?id=" + linguagem.getId();
  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name("redirect:/linguagem")
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirLinguagem executou e mensagem de sucesso"
  		,() -> verify(linguagemService, times(1)).excluirLinguagem(linguagem.getId())
    	,() -> assertEquals(MESSAGE_DELETE_SUCCESS, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("4.1. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = EXCLUIR + "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new LinguagemNaoEncontradaException(ERROR_MESSAGE_NAO_ENCONTRADO)).when(linguagemService).excluirLinguagem(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirLinguagem executou e mensagem de erro"
  		,() -> verify(linguagemService, times(1)).excluirLinguagem(ID_INVALIDO)
  		,() -> assertEquals(MESSAGE_DELETE_ERROR + ERROR_MESSAGE_NAO_ENCONTRADO, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5. Quando POST é chamado em editar com uma nova Linguagem válida então deve salvar")
  void whenPOSTIsCalledInEditarWithANewValidLinguagemThenShouldSave() throws Exception {
  	//given
  	Linguagem novaLinguagem = new LinguagemBuilder().withNome("Java").build();
  	Linguagem linguagemSalva = new LinguagemBuilder().withId(1L).withNome("Java").build();
  			
  	//when
  	when(linguagemService.salvarLinguagem(novaLinguagem)).thenReturn(linguagemSalva);
  	when(linguagemService.obterLinguagem(linguagemSalva.getId())).thenReturn(linguagemSalva);
		
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR)
  					.param("nome", novaLinguagem.getNome())
  				)
  				.andExpectAll(
  						status().isOk(),
    					view().name("linguagem/form.html")
  				)
  				.andReturn()
  				.getModelAndView();
  	
  	assertAll(
  		"verifica se serviço salvarLinguagem executou e retorna linguagem salva e mensagem de sucesso"
  		,() -> verify(linguagemService, times(1)).salvarLinguagem(novaLinguagem)
  		,() -> assertModelAttributeValue(mv, LINGUAGEM, linguagemSalva)
  		,() -> assertModelAttributeValue(mv, MENSAGEM, MESSAGE_SAVE_SUCCESS)
    );

  }
  
  @Test
  @DisplayName("5.1. Quando POST é chamado em editar com uma Linguagem inválida então deve retornar erro")
  void whenPOSTIsCalledInEditarWithAInvalidLinguagemThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR))
  				.andReturn()
  				.getModelAndView();
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), LINGUAGEM)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
	
}
