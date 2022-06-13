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
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;

@Controller
@RequestMapping("desenvolvedor")
public class DesenvolvedorController {
	
	private final String DESENVOLVEDOR = "desenvolvedor";
	private final String MENSAGEM = "mensagem";
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@GetMapping("/editar")
	public ModelAndView editarDesenvolvedor(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("desenvolvedor/form.html");
		
		obterDesenvolvedor(id, mv);
		
		mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarDesenvolvedor(
			@Valid Desenvolvedor desenvolvedor
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView("desenvolvedor/form.html");
		
		if(bindingResult.hasErrors()) {
			mv.addObject(DESENVOLVEDOR, desenvolvedor);
			return mv;
		}
		
		salvarDesenvolvedor(desenvolvedor);
		
		obterDesenvolvedor(desenvolvedor.getId(),mv);
		
		mv.addObject(MENSAGEM,"Desenvolvedor salvo com sucesso.");
		mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
		
		return mv;
		
	}


	@GetMapping
	public ModelAndView listarDesenvolvedores(String nome, String quatroLetras) {
		
		ModelAndView mv = new ModelAndView("desenvolvedor/listar.html");
		
		mv.addObject("lista", desenvolvedorService.listarDesenvolvedores(nome, quatroLetras));
		
		mv.addObject("nome", nome);
		mv.addObject("quatroLetras", quatroLetras);
	
		return mv;
		
	}
	
	@GetMapping("/detalhes")
	public ModelAndView detalheProjeto(Long id) {
		
		ModelAndView mv = new ModelAndView("desenvolvedor/detalhes.html");
		
		obterDesenvolvedor(id, mv);
		
		return mv;
		
	}


	@GetMapping("/linguagem")
	@ResponseBody
	public List<Desenvolvedor> listarDesenvolvedoresPorLinguagem(@RequestParam Long id) {
		
		List<Desenvolvedor> desenvolvedores;
		
		try {
			desenvolvedores = linguagemService.obterLinguagem(id).getDesenvolvedores();
		} catch (Exception e) {
			desenvolvedores = new ArrayList<>(); 
		}
		
		return desenvolvedores;
		
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluirDesenvolvedor(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/desenvolvedor");
		
		try {
			desenvolvedorService.excluirDesenvolvedor(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, "Desenvolvedor excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, "Erro ao excluir! " + e.getMessage());
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
	
	private void salvarDesenvolvedor(Desenvolvedor desenvolvedor) {
		
		desenvolvedor.setQuatroLetras(desenvolvedor.getQuatroLetras().toUpperCase());
		desenvolvedor.setEmail(desenvolvedor.getEmail().toLowerCase());
		
		desenvolvedorService.salvarDesenvolvedor(desenvolvedor);
	
	}
}
