package com.gft.projetos.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.builders.DesenvolvedorBuilder;
import com.gft.projetos.entities.builders.LinguagemBuilder;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;
import com.gft.projetos.utils.DesenvolvedorUtils;

import static com.gft.projetos.controllers.DesenvolvedorController.DESENVOLVEDOR;
import static com.gft.projetos.controllers.DesenvolvedorController.MENSAGEM;
import static com.gft.projetos.controllers.DesenvolvedorController.LISTA;

import static com.gft.projetos.controllers.DesenvolvedorController.MESSAGE_SAVE_SUCCESS;
import static com.gft.projetos.controllers.DesenvolvedorController.MESSAGE_DELETE_SUCCESS;
import static com.gft.projetos.controllers.DesenvolvedorController.MESSAGE_DELETE_ERROR;

import static com.gft.projetos.services.DesenvolvedorService.ERROR_MESSAGE_NAO_ENCONTRADO;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do DesenvolvedorController")
class DesenvolvedorControllerTest {

	static final String PATH = "/desenvolvedor";
	static final String EDITAR = PATH + "/editar";
	static final String DETALHES = PATH + "/detalhes";
	static final String EXCLUIR = PATH + "/excluir";
	static final String LINGUAGEM = PATH + "/linguagem";
	
	static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
  @Mock
  private DesenvolvedorService desenvolvedorService;
  
  @Mock
  private LinguagemService linguagemService;

  @InjectMocks
  private DesenvolvedorController desenvolvedorController;

  @BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(desenvolvedorController).build();
  }

 
  @Test
  @DisplayName("1. Quando GET é chamado então status ok, arquivo correto e lista com desenvolvedor são retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListDesenvolvedorAreReturned() throws Exception {
  	//given
  	List<Desenvolvedor> desenvolvedorLista = Collections.singletonList(DesenvolvedorUtils.createFakeEntity());
  	
  	//when
  	when(desenvolvedorService.listarDesenvolvedores(null,null)).thenReturn(desenvolvedorLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH))
  			.andExpectAll(
  					status().isOk(),
  					view().name("desenvolvedor/listar.html")
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Desenvolvedores"
  		,() -> assertModelAttributeAvailable(mv, LISTA)
  		,() -> assertCompareListModelAttribute(mv, LISTA, desenvolvedorLista)
  	);
  	
  }
  
  @Test
  @DisplayName("2. Quando GET é chamado em editar então status ok e arquivo correto são retornados")
  void whenGETIsCalledInEditarThenStatusOkAndCorrectFileAreReturned() throws Exception {

  	//then
  	mockMvc.perform(get(EDITAR))
  			.andExpectAll(
  					status().isOk(),
  					view().name("desenvolvedor/form.html")
  			);
  	
  }
  
  	
  @Test
  @DisplayName("2.1. Quando GET é chamado em editar sem uma id então um novo Desenvolvedor é retornado")
  void whenGETIsCalledInEditarWithoutIdThenANewDesenvolvedorIsReturned() throws Exception {

  	//then
  	ModelAndView mv = mockMvc.perform(get(EDITAR))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, DESENVOLVEDOR, new Desenvolvedor());
  
  }
  
  @Test
  @DisplayName("2.2. Quando GET é chamado em editar com id válido então um Desenvolvedor válido é retornado")
  void whenGETIsCalledInEditarWithValidIdThenAValidDesenvolvedorIsReturned() throws Exception {
  	//given
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	String caminho = EDITAR + "?id=" + desenvolvedor.getId();
  	
  	//when
  	when(desenvolvedorService.obterDesenvolvedor(desenvolvedor.getId())).thenReturn(desenvolvedor);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, DESENVOLVEDOR, desenvolvedor);
  
  }
  
  @Test
  @DisplayName("2.3. Quando GET é chamado em editar com id inválido então um novo Desenvolvedor e mensagem são retornados")
  void whenGETIsCalledInEditarWithInvalidIdThenANewDesenvolvedorAndMensagemAreReturned() throws Exception {
  	//given
  	String caminho = EDITAR + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(desenvolvedorService.obterDesenvolvedor(ID_INVALIDO)).thenThrow(DesenvolvedorNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se é desenvolvedor novo e existe mensagem"
  		,() ->	assertModelAttributeValue(mv, DESENVOLVEDOR, new Desenvolvedor())
  		,() ->	assertModelAttributeAvailable(mv, MENSAGEM)
  	);
  			
  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhes com id válido então um Desenvolvedor válido é retornado")
  void whenGETIsCalledInDetalhesWithValidIdThenAValidDesenvolvedorIsReturned() throws Exception {
  	//given
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	String caminho = DETALHES + "?id=" + desenvolvedor.getId();
  	
  	//when
  	when(desenvolvedorService.obterDesenvolvedor(desenvolvedor.getId())).thenReturn(desenvolvedor);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name("desenvolvedor/detalhes.html")
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, DESENVOLVEDOR, desenvolvedor);
  
  }
  
  @Test
  @DisplayName("3.1. Quando GET é chamado em detalhes com id inválido então mensagem é retornada")
  void whenGETIsCalledInDetalhesWithInvalidIdThenMensagemIsReturned() throws Exception {
  	//given
  	String caminho = DETALHES + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(desenvolvedorService.obterDesenvolvedor(ID_INVALIDO)).thenThrow(DesenvolvedorNaoEncontradoException.class);
  	
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
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	String caminho = EXCLUIR + "?id=" + desenvolvedor.getId();
  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name("redirect:/desenvolvedor")
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirDesenvolvedor executou e mensagem de sucesso"
  		,() -> verify(desenvolvedorService, times(1)).excluirDesenvolvedor(desenvolvedor.getId())
    	,() -> assertEquals(MESSAGE_DELETE_SUCCESS, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("4.1. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = EXCLUIR + "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new DesenvolvedorNaoEncontradoException(ERROR_MESSAGE_NAO_ENCONTRADO)).when(desenvolvedorService).excluirDesenvolvedor(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirDesenvolvedor executou e mensagem de erro"
  		,() -> verify(desenvolvedorService, times(1)).excluirDesenvolvedor(ID_INVALIDO)
  		,() -> assertEquals(MESSAGE_DELETE_ERROR + ERROR_MESSAGE_NAO_ENCONTRADO, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5. Quando POST é chamado em editar com um novo Desenvolvedor válido então deve salvar")
  void whenPOSTIsCalledInEditarWithANewValidDesenvolvedorThenShouldSave() throws Exception {
  	//given
  	Desenvolvedor novoDesenvolvedor = new DesenvolvedorBuilder()
  																							.withNome("Bill Gates")
  																							.withQuatroLetras("BIGA")
  																							.withEmail("biga@gft.com")
  																							.withSalarioMensal(new BigDecimal("50000.00"))
  																							.build();
  	Desenvolvedor desenvolvedorSalvo = new DesenvolvedorBuilder()
  																							.withId(1L)
                                        				.withNome("Bill Gates")
                                        				.withQuatroLetras("BIGA")
                                        				.withEmail("biga@gft.com")
                                        				.withSalarioMensal(new BigDecimal("50000.00"))
                                        				.build();
  			
  	//when
  	when(desenvolvedorService.salvarDesenvolvedor(novoDesenvolvedor)).thenReturn(desenvolvedorSalvo);
  	when(desenvolvedorService.obterDesenvolvedor(desenvolvedorSalvo.getId())).thenReturn(desenvolvedorSalvo);
		
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR)
  					.param("nome", novoDesenvolvedor.getNome())
  					.param("quatroLetras", novoDesenvolvedor.getQuatroLetras())
  					.param("email", novoDesenvolvedor.getEmail())
  					.param("salarioMensal", String.valueOf(novoDesenvolvedor.getSalarioMensal()))
  				)
  				.andExpectAll(
  						status().isOk(),
    					view().name("desenvolvedor/form.html")
  				)
  				.andReturn()
  				.getModelAndView();
  	
  	assertAll(
  		"verifica se serviço salvarDesenvolvedor executou e retorna desenvolvedor salvo e mensagem de sucesso"
  		,() -> verify(desenvolvedorService, times(1)).salvarDesenvolvedor(novoDesenvolvedor)
  		,() -> assertModelAttributeValue(mv, DESENVOLVEDOR, desenvolvedorSalvo)
  		,() -> assertModelAttributeValue(mv, MENSAGEM, MESSAGE_SAVE_SUCCESS)
    );

  }
  
  @Test
  @DisplayName("5.1. Quando POST é chamado em editar com um Desenvolvedor inválido então deve retornar erro")
  void whenPOSTIsCalledInEditarWithAInvalidDesenvolvedorThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR))
  				.andReturn()
  				.getModelAndView();
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), DESENVOLVEDOR)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
  
  
  @Test
  @DisplayName("6. Quando GET é chamado em linguagem com uma Linguagem Válida então deve retornar Lista de Desenvolvedores")
  void whenGETIsCalledInLinguagemWithAValidLinguagemThenShouldReturnDevelopersList() throws Exception {
		//given
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	Desenvolvedor desenvolvedor2 = DesenvolvedorUtils.createFakeEntity2();
  	
  	Linguagem linguagem = new LinguagemBuilder()
  															.withId(1L)
  															.withNome("Java")
  															.withDesenvolvedores(new ArrayList<>())
  															.build();
  	
  	linguagem.getDesenvolvedores().add(desenvolvedor);
  	linguagem.getDesenvolvedores().add(desenvolvedor2);
  	
  	String caminho = LINGUAGEM + "?id=" + linguagem.getId();
  	
  	//when
  	when(linguagemService.obterLinguagem(linguagem.getId())).thenReturn(linguagem);
  	
  	//then
  	mockMvc.perform(get(caminho))
  				.andExpectAll(
  					status().isOk()
  					,jsonPath("$.[0].id", is(Math.toIntExact(desenvolvedor.getId())))
  					,jsonPath("$.[0].nome", is(desenvolvedor.getNome()))
  					,jsonPath("$.[0].quatroLetras", is(desenvolvedor.getQuatroLetras()))
  					,jsonPath("$.[0].email", is(desenvolvedor.getEmail()))
  					,jsonPath("$.[0].salarioMensal", is(desenvolvedor.getSalarioMensal().doubleValue()))
  					,jsonPath("$.[1].id", is(Math.toIntExact(desenvolvedor2.getId())))
  					,jsonPath("$.[1].nome", is(desenvolvedor2.getNome()))
  					,jsonPath("$.[1].quatroLetras", is(desenvolvedor2.getQuatroLetras()))
  					,jsonPath("$.[1].email", is(desenvolvedor2.getEmail()))
  					,jsonPath("$.[1].salarioMensal", is(desenvolvedor2.getSalarioMensal().doubleValue()))
  				);
  				  	
  }
  
  @Test
  @DisplayName("6.1. Quando GET é chamado em linguagem com uma Linguagem Inválida então deve retornar Lista de Desenvolvedores vazia")
  void whenGETIsCalledInLinguagemWithAInvalidLinguagemThenShouldReturnEmptyDevelopersList() throws Exception {
  	//given  	
  	String caminho = LINGUAGEM + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(linguagemService.obterLinguagem(ID_INVALIDO)).thenThrow(LinguagemNaoEncontradaException.class);
  	
  	//then
  	mockMvc.perform(get(caminho))
  				.andExpectAll(
  					status().isOk()
  					, content().contentType(MediaType.APPLICATION_JSON)
  					, content().string("[]")
  				);
  				  	
  }
	
}
