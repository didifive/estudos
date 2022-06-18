package com.gft.projetos.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.entities.builders.ProjetoBuilder;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoNoProjetoException;
import com.gft.projetos.exceptions.ProjetoNaoEncontradoException;
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;
import com.gft.projetos.services.ProjetoService;
import com.gft.projetos.utils.DesenvolvedorUtils;
import com.gft.projetos.utils.ProjetoUtils;

import static com.gft.projetos.controllers.ProjetoController.PROJETO;
import static com.gft.projetos.controllers.ProjetoController.MENSAGEM;
import static com.gft.projetos.controllers.ProjetoController.LISTA;

import static com.gft.projetos.controllers.ProjetoController.MESSAGE_SAVE_SUCCESS;
import static com.gft.projetos.controllers.ProjetoController.MESSAGE_DELETE_SUCCESS;
import static com.gft.projetos.controllers.ProjetoController.MESSAGE_DELETE_ERROR;
import static com.gft.projetos.controllers.ProjetoController.MESSAGE_REMOVE_DESENVOLVEDOR_SUCCESS;
import static com.gft.projetos.controllers.ProjetoController.MESSAGE_REMOVE_DESENVOLVEDOR_ERROR;

import static com.gft.projetos.services.DesenvolvedorService.ERROR_MESSAGE_NAO_ENCONTRADO;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do ProjetoController")
class ProjetoControllerTest {

	static final String PATH = "/projeto";
	static final String EDITAR = PATH + "/editar";
	static final String DETALHES = PATH + "/detalhes";
	static final String EXCLUIR = PATH + "/excluir";
	static final String REMOVER_DESENVOLVEDOR = DETALHES + "/remover-desenvolvedor";
	
	static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
  @Mock
  private ProjetoService projetoService;
  
  @Mock
  private DesenvolvedorService desenvolvedorService;
  
  @Mock
  private LinguagemService linguagemService;

  @InjectMocks
  private ProjetoController projetoController;

  @BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(projetoController).build();
  }

 
  @Test
  @DisplayName("1. Quando GET é chamado então status ok, arquivo view correto e lista com projeto são retornados")
  void whenGETIsCalledThenStatusOkCorrectViewFileAndListWithProjetoAreReturned() throws Exception {
  	//given
  	List<Projeto> projetoLista = Collections.singletonList(ProjetoUtils.createFakeEntity());
  	
  	//when
  	when(projetoService.listarProjetos(null,null)).thenReturn(projetoLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH))
  			.andExpectAll(
  					status().isOk(),
  					view().name("projeto/listar.html")
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Projetos"
  		,() -> assertModelAttributeAvailable(mv, LISTA)
  		,() -> assertCompareListModelAttribute(mv, LISTA, projetoLista)
  	);
  	
  }
  
  @Test
  @DisplayName("2. Quando GET é chamado em editar então status ok e arquivo view correto são retornados")
  void whenGETIsCalledInEditarThenStatusOkAndCorrectViewFileAreReturned() throws Exception {

  	//then
  	mockMvc.perform(get(EDITAR))
  			.andExpectAll(
  					status().isOk(),
  					view().name("projeto/form.html")
  			);
  	
  }
  
  	
  @Test
  @DisplayName("2.1. Quando GET é chamado em editar sem uma id então um novo Projeto é retornado")
  void whenGETIsCalledInEditarWithoutIdThenANewProjetoIsReturned() throws Exception {

  	//then
  	ModelAndView mv = mockMvc.perform(get(EDITAR))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, PROJETO, new Projeto());
  
  }
  
  @Test
  @DisplayName("2.2. Quando GET é chamado em editar com id válido então um Projeto válido é retornado")
  void whenGETIsCalledInEditarWithValidIdThenAValidProjetoIsReturned() throws Exception {
  	//given
  	Projeto projeto = ProjetoUtils.createFakeEntity();
  	String caminho = EDITAR + "?id=" + projeto.getId();
  	
  	//when
  	when(projetoService.obterProjeto(projeto.getId())).thenReturn(projeto);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, PROJETO, projeto);
  
  }
  
  @Test
  @DisplayName("2.3. Quando GET é chamado em editar com id inválido então um novo Projeto e mensagem são retornados")
  void whenGETIsCalledInEditarWithInvalidIdThenANewProjetoAndMensagemAreReturned() throws Exception {
  	//given
  	String caminho = EDITAR + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(projetoService.obterProjeto(ID_INVALIDO)).thenThrow(ProjetoNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se é projeto novo e existe mensagem"
  		,() ->	assertModelAttributeValue(mv, PROJETO, new Projeto())
  		,() ->	assertModelAttributeAvailable(mv, MENSAGEM)
  	);
  			
  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhes com id válido então um Projeto válido é retornado")
  void whenGETIsCalledInDetalhesWithValidIdThenAValidProjetoIsReturned() throws Exception {
  	//given
  	Projeto projeto = ProjetoUtils.createFakeEntity();
  	String caminho = DETALHES + "?id=" + projeto.getId();
  	
  	//when
  	when(projetoService.obterProjeto(projeto.getId())).thenReturn(projeto);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name("projeto/detalhes.html")
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, PROJETO, projeto);
  
  }
  
  @Test
  @DisplayName("3.1. Quando GET é chamado em detalhes com id inválido então mensagem é retornada")
  void whenGETIsCalledInDetalhesWithInvalidIdThenMensagemIsReturned() throws Exception {
  	//given
  	String caminho = DETALHES + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(projetoService.obterProjeto(ID_INVALIDO)).thenThrow(ProjetoNaoEncontradoException.class);
  	
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
  	Projeto projeto = ProjetoUtils.createFakeEntity();
  	String caminho = EXCLUIR + "?id=" + projeto.getId();
  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name("redirect:/projeto")
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirDesenvolvedor executou e mensagem de sucesso"
  		,() -> verify(projetoService, times(1)).excluirProjeto(projeto.getId())
    	,() -> assertEquals(MESSAGE_DELETE_SUCCESS, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("4.1. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = EXCLUIR + "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new ProjetoNaoEncontradoException(ERROR_MESSAGE_NAO_ENCONTRADO)).when(projetoService).excluirProjeto(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirDesenvolvedor executou e mensagem de erro"
  		,() -> verify(projetoService, times(1)).excluirProjeto(ID_INVALIDO)
  		,() -> assertEquals(MESSAGE_DELETE_ERROR + ERROR_MESSAGE_NAO_ENCONTRADO, flashMap.get(MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5. Quando POST é chamado em editar com um novo Projeto válido então deve salvar")
  void whenPOSTIsCalledInEditarWithANewValidProjetoThenShouldSave() throws Exception {
  	//given
  	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	
  	final Long ID = 1L;
  	final String NOME = "Windows 95";
  	final String APELIDO = "W95";
  	final String DATAENTREGA_STRING = "2022-06-18";
  	final Date DATAENTREGA = formatter.parse(DATAENTREGA_STRING);
  	final BigDecimal ORCAMENTO = new BigDecimal("50000.00");
  	
  	Projeto novoProjeto = new ProjetoBuilder()
																	.withNome(NOME)
																	.withApelido(APELIDO)
																	.withDataEntrega(DATAENTREGA)
																	.withOrcamento(ORCAMENTO)
																	.build();
  	Projeto projetoSalvo = new ProjetoBuilder()
																	.withId(ID)
                          				.withNome(NOME)
                          				.withApelido(APELIDO)
																	.withDataEntrega(DATAENTREGA)
																	.withOrcamento(ORCAMENTO)
                          				.build();
  			
  	//when
  	when(projetoService.salvarProjeto(novoProjeto)).thenReturn(projetoSalvo);
  	when(projetoService.obterProjeto(projetoSalvo.getId())).thenReturn(projetoSalvo);
		
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR)
  					.param("nome", novoProjeto.getNome())
  					.param("apelido", novoProjeto.getApelido())
  					.param("dataEntrega", DATAENTREGA_STRING)
  					.param("orcamento", String.valueOf(novoProjeto.getOrcamento()))
  				)
  				.andExpectAll(
  						status().isOk(),
    					view().name("projeto/form.html")
  				)
  				.andReturn()
  				.getModelAndView();
  	
  	assertAll(
  		"verifica se serviço salvarProjeto executou e retorna projeto salvo e mensagem de sucesso"
  		,() -> verify(projetoService, times(1)).salvarProjeto(novoProjeto)
  		,() -> assertModelAttributeValue(mv, PROJETO, projetoSalvo)
  		,() -> assertModelAttributeValue(mv, MENSAGEM, MESSAGE_SAVE_SUCCESS)
    );

  }
  
  @Test
  @DisplayName("5.1. Quando POST é chamado em editar com um Projeto inválido então deve retornar erro")
  void whenPOSTIsCalledInEditarWithAInvalidProjetoThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(EDITAR))
  				.andReturn()
  				.getModelAndView();
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), PROJETO)
  								.hasErrors();
  	
  	assertTrue(containError);

  }

  @Test
  @DisplayName("6. Quando GET é chamado em remover desenvolvedor com id de Projeto e id de Desenvolvedor válidos então mensagem de sucesso é retornada")
  void whenGETIsCalledInRemoverDesenvolvedorWithValidProjetoIdAndDesenvolvedorIdThenSuccessMessageShouldBeReturn() throws Exception {
  	//given
  	Projeto projeto = ProjetoUtils.createFakeEntity();
  	
  	Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
  	
  	String caminho = REMOVER_DESENVOLVEDOR 
  											+ "?idProjeto=" + projeto.getId()
  											+ "&idDesenvolvedor=" + desenvolvedor.getId();
  	
  	//when
  	doNothing().when(projetoService).removerDesenvolvedorDoProjeto(projeto.getId(), desenvolvedor.getId());
  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name("redirect:/" + PROJETO + "?id=" + projeto.getId())
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
    		"verifica se serviço removerDesenvolvedorDoProjeto executou e retorna mensagem de sucesso"
    		,() -> verify(projetoService, times(1)).removerDesenvolvedorDoProjeto(projeto.getId(), desenvolvedor.getId())
    		,() -> assertEquals(MESSAGE_REMOVE_DESENVOLVEDOR_SUCCESS, flashMap.get(MENSAGEM))
      );
  	
  }
  
  @Test
  @DisplayName("6.1. Quando GET é chamado em remover desenvolvedor com id de Projeto ou id Desenvolvedor inválidos então mensagem de erro é retornada")
  void whenGETIsCalledInRemoverDesenvolvedorWithInvalidProjetoIdOrDesenvolvedorIdThenErrorMenssageShouldBeReturn() throws Exception {
  	//given
  	final String MENSAGEM_ERRO = "Desenvolvedor não foi encontrado no projeto.";
  	String caminho = REMOVER_DESENVOLVEDOR 
  											+ "?idProjeto=" + ID_INVALIDO
  											+ "&idDesenvolvedor=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new DesenvolvedorNaoEncontradoNoProjetoException(MENSAGEM_ERRO)).when(projetoService).removerDesenvolvedorDoProjeto(ID_INVALIDO, ID_INVALIDO);
  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name("redirect:/" + PROJETO + "?id=" + ID_INVALIDO)
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
    		"verifica se serviço removerDesenvolvedorDoProjeto executou e retorna mensagem de sucesso"
    		,() -> verify(projetoService, times(1)).removerDesenvolvedorDoProjeto(ID_INVALIDO, ID_INVALIDO)
    		,() -> assertEquals(MESSAGE_REMOVE_DESENVOLVEDOR_ERROR + MENSAGEM_ERRO, flashMap.get(MENSAGEM))
      );
  	
  }
	
}
