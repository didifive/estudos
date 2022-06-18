package com.gft.projetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.exceptions.LinguagemNaoEncontradaException;
import com.gft.projetos.services.LinguagemService;

@Controller
@RequestMapping("linguagem")
public class LinguagemController {
	
	//ModelAndView Objects
	protected static final String LINGUAGEM = "linguagem";
	protected static final String MENSAGEM = "mensagem";
	protected static final String LISTA = "lista";
	
	//Path and files Views of Linguagem
	protected static final String FORM = LINGUAGEM + "/form.html";
	protected static final String LISTAR = LINGUAGEM + "/listar.html";
	protected static final String DETALHES = LINGUAGEM + "/detalhes.html";
	
	//Messages
	protected static final String MESSAGE_SAVE_SUCCESS = "Linguagem salva com sucesso.";
	protected static final String MESSAGE_DELETE_SUCCESS = "Linguagem excluída com sucesso.";
	protected static final String MESSAGE_DELETE_ERROR = "Erro ao excluir linguagem! ";
	
	@Autowired
	private LinguagemService linguagemService;
	
	@GetMapping("/editar")
	public ModelAndView editarLinguagem(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(FORM);
		
		obterLinguagem(id, mv);
		
		return mv;
	
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarLinguagem(
			@Valid Linguagem linguagem
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView(FORM);
		
		if(bindingResult.hasErrors()) {
			mv.addObject(LINGUAGEM, linguagem);
			return mv;
		}
		
		linguagem = salvarLinguagem(linguagem);
		
		obterLinguagem(linguagem.getId(),mv);
		
		mv.addObject(MENSAGEM, MESSAGE_SAVE_SUCCESS);
		
		return mv;
		
	}
	
	@GetMapping
	public ModelAndView listarLinguagens() {
		
		ModelAndView mv = new ModelAndView(LISTAR);
		
		mv.addObject(LISTA, linguagemService.listarLinguagens());
		
		return mv;
		
	}
	
	@GetMapping("/detalhes")
	public ModelAndView detalheLinguagem(Long id) {
		
		ModelAndView mv = new ModelAndView(DETALHES);
		
		obterLinguagem(id, mv);
		
		return mv;
		
	}
		
	@GetMapping("/excluir")
	public ModelAndView excluirLinguagem(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/"+LINGUAGEM);
		
		try {
			linguagemService.excluirLinguagem(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_SUCCESS);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_ERROR + e.getMessage());
		}
		
		
		return mv;
		
	}
	
	private void obterLinguagem(Long id, ModelAndView mv) {
		
		Linguagem linguagem;
		
		if(id == null) {
			linguagem = new Linguagem();
		} else {
  		try {
  			linguagem = linguagemService.obterLinguagem(id);
  		} catch (LinguagemNaoEncontradaException e) {
  			linguagem = new Linguagem();
  			mv.addObject(MENSAGEM,e.getMessage());
  		}
		}
		
		mv.addObject(LINGUAGEM, linguagem);

	}
	
	private Linguagem salvarLinguagem(Linguagem linguagem) {
		
		return linguagemService.salvarLinguagem(linguagem);
	
	}
	
}
