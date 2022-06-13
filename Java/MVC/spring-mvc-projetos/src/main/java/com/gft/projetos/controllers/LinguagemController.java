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
	
	private final String LINGUAGEM = "linguagem";
	private final String MENSAGEM = "mensagem";
	
	@Autowired
	private LinguagemService linguagemService;
	
	@GetMapping("/editar")
	public ModelAndView editarLinguagem(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("linguagem/form.html");
		
		obterLinguagem(id, mv);
		
		return mv;
	
	}
	
	@PostMapping("/editar")
	public ModelAndView salvarLinguagem(
			@Valid Linguagem linguagem
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView("linguagem/form.html");
		
		if(bindingResult.hasErrors()) {
			mv.addObject(LINGUAGEM, linguagem);
			return mv;
		}
		
		salvarLinguagem(linguagem);
		
		obterLinguagem(linguagem.getId(),mv);
		
		mv.addObject(MENSAGEM,"Linguagem salva com sucesso.");
		
		return mv;
		
	}
	
	@GetMapping
	public ModelAndView listarLinguagens() {
		
		ModelAndView mv = new ModelAndView("linguagem/listar.html");
		
		mv.addObject("lista", linguagemService.listarLinguagens());
		
		return mv;
		
	}
	
	@GetMapping("/detalhes")
	public ModelAndView detalheLinguagem(Long id) {
		
		ModelAndView mv = new ModelAndView("linguagem/detalhes.html");
		
		obterLinguagem(id, mv);
		
		return mv;
		
	}
		
	@RequestMapping("/excluir")
	public ModelAndView excluirLinguagem(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/linguagem");
		
		try {
			linguagemService.excluirLinguagem(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, "Linguagem excluída com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, "Erro ao excluir linguagem! " + e.getMessage());
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
	
	private void salvarLinguagem(Linguagem linguagem) {
		
		linguagemService.salvarLinguagem(linguagem);
	
	}
	
}
