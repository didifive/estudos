package com.gft.projetos.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;

@Controller
@RequestMapping("desenvolvedor")
public class DesenvolvedorController {
	
	//ModelAndView Objects
	protected static final String DESENVOLVEDOR = "desenvolvedor";
	protected static final String MENSAGEM = "mensagem";
	protected static final String LISTA = "lista";
	protected static final String LISTA_LINGUAGENS = "listaLinguagens";
	
	//Path and files Views of Desenvolvedor
	protected static final String FORM = DESENVOLVEDOR + "/form.html";
	protected static final String LISTAR = DESENVOLVEDOR + "/listar.html";
	protected static final String DETALHES = DESENVOLVEDOR + "/detalhes.html";
	
	//Messages
	protected static final String MESSAGE_SAVE_SUCCESS = "Desenvolvedor salvo com sucesso.";
	protected static final String MESSAGE_DELETE_SUCCESS = "Desenvolvedor excluído com sucesso.";
	protected static final String MESSAGE_DELETE_ERROR = "Erro ao excluir desenvolvedor! ";
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@GetMapping("/editar")
	public ModelAndView editarDesenvolvedor(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(FORM);
		
		obterDesenvolvedor(id, mv);
		
		mv.addObject(LISTA_LINGUAGENS,linguagemService.listarLinguagens());
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarDesenvolvedor(
			@Valid Desenvolvedor desenvolvedor
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView(FORM);
		
		if(bindingResult.hasErrors()) {
			mv.addObject(DESENVOLVEDOR, desenvolvedor);
			return mv;
		}
		
		desenvolvedor = salvarDesenvolvedor(desenvolvedor);
		
		obterDesenvolvedor(desenvolvedor.getId(),mv);
		
		mv.addObject(MENSAGEM,MESSAGE_SAVE_SUCCESS);
		mv.addObject(LISTA_LINGUAGENS,linguagemService.listarLinguagens());
		
		return mv;
		
	}


	@GetMapping
	public ModelAndView listarDesenvolvedores(String nome, String quatroLetras) {
		
		ModelAndView mv = new ModelAndView(LISTAR);
		
		mv.addObject(LISTA, desenvolvedorService.listarDesenvolvedores(nome, quatroLetras));
		
		mv.addObject("nome", nome);
		mv.addObject("quatroLetras", quatroLetras);
	
		return mv;
		
	}
	
	@GetMapping("/detalhes")
	public ModelAndView detalheProjeto(Long id) {
		
		ModelAndView mv = new ModelAndView(DETALHES);
		
		obterDesenvolvedor(id, mv);
		
		return mv;
		
	}


	@GetMapping("/linguagem")
	@ResponseBody
	public List<Desenvolvedor> listarDesenvolvedoresPorLinguagem(@RequestParam Long id) {
		
		List<Desenvolvedor> desenvolvedores;
		
		try {
			desenvolvedores = linguagemService.obterLinguagem(id).getDesenvolvedores();
		} catch (LinguagemNaoEncontradaException e) {
			desenvolvedores = new ArrayList<>(); 
		}
		
		return desenvolvedores;
		
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirDesenvolvedor(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/"+DESENVOLVEDOR);
		
		try {
			desenvolvedorService.excluirDesenvolvedor(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_SUCCESS);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_ERROR + e.getMessage());
		}
		
		return mv;
		
	}
	
	private void obterDesenvolvedor(Long id, ModelAndView mv) {
		Desenvolvedor desenvolvedor;
		
		if(id == null) {
			desenvolvedor = new Desenvolvedor();
		} else {
  		try {
  			desenvolvedor = desenvolvedorService.obterDesenvolvedor(id);
  		} catch (DesenvolvedorNaoEncontradoException e) {
  			desenvolvedor = new Desenvolvedor();
  			mv.addObject(MENSAGEM,e.getMessage());
  		}
		}
		
		mv.addObject(DESENVOLVEDOR, desenvolvedor);

	}
	
	private Desenvolvedor salvarDesenvolvedor(Desenvolvedor desenvolvedor) {
		
		if(!desenvolvedor.getQuatroLetras().isBlank())
			desenvolvedor.setQuatroLetras(desenvolvedor.getQuatroLetras().toUpperCase());
		if(!desenvolvedor.getEmail().isBlank())
			desenvolvedor.setEmail(desenvolvedor.getEmail().toLowerCase());
		
		return desenvolvedorService.salvarDesenvolvedor(desenvolvedor);
	
	}
}
