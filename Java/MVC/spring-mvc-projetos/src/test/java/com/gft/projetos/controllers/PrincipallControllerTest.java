package com.gft.projetos.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do PrincipalController")
class PrincipallControllerTest {
	
	static final String PATH = "/";
	static final String SOBRE = PATH + "sobre";
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private PrincipalController principalController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(principalController).build();
  }

	@Test
	@DisplayName("1. Quando GET é chamado então status ok e arquivo de View correto são retornados")
	void whenGETIsCalledThenThenStatusOkCorrectViewFileAreReturned() throws Exception {
		//then
  	mockMvc.perform(get(PATH))
  			.andExpectAll(
  					status().isOk(),
  					view().name("index.html")
  			)
      	.andReturn()
  			.getModelAndView();
	}
	
	@Test
	@DisplayName("1.1. Quando GET é chamado em sobre então status ok e arquivo de View correto são retornados")
	void whenGETIsCalledInSobreThenThenStatusOkCorrectViewFileAreReturned() throws Exception {
		//then
  	mockMvc.perform(get(SOBRE))
  			.andExpectAll(
  					status().isOk(),
  					view().name("sobre.html")
  			)
      	.andReturn()
  			.getModelAndView();
	}
}
