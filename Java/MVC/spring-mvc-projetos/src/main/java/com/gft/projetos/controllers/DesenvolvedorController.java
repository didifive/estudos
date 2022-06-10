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
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;

@Controller
@RequestMapping("desenvolvedor")
public class DesenvolvedorController {
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@GetMapping("/editar")
	public ModelAndView editarDesenvolvedor(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("desenvolvedor/form.html");
		
		Desenvolvedor desenvolvedor;
		
		if(id==null) {
			mv.addObject("desenvolvedor",new Desenvolvedor());
			mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
		} else {
			try {
				desenvolvedor = desenvolvedorService.obterDesenvolvedor(id);
				mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
			} catch (Exception e) {
				desenvolvedor = new Desenvolvedor();
				mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
				mv.addObject("mensagem",e.getMessage());
			}
			mv.addObject("desenvolvedor", desenvolvedor);
		}
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarDesenvolvedor(@Valid Desenvolvedor desenvolvedor, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("desenvolvedor/form.html");
		
		boolean novo = true;
		
		if(desenvolvedor.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {
			mv.addObject("desenvolvedor", desenvolvedor);
			return mv;
		}
		
		desenvolvedor.setQuatroLetras(desenvolvedor.getQuatroLetras().toUpperCase());
		desenvolvedor.setEmail(desenvolvedor.getEmail().toLowerCase());
		desenvolvedorService.salvarDesenvolvedor(desenvolvedor);
		
		if(novo) {
			mv.addObject("desenvoldedor", new Desenvolvedor());
		} else {
			mv.addObject("desenvolvedor", desenvolvedor);
		}
		
		mv.addObject("mensagem","Desenvolvedor salvo com sucesso.");
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
		
		Desenvolvedor desenvolvedor;
		
		try {
			desenvolvedor = desenvolvedorService.obterDesenvolvedor(id);
		} catch (Exception e) {
			desenvolvedor = new Desenvolvedor();
			mv.addObject("mensagem",e.getMessage());
		}
		
		mv.addObject("desenvolvedor", desenvolvedor);
		
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
	public ModelAndView excluirDesenvolvedor(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/desenvolvedor");
		
		try {
			desenvolvedorService.excluirDesenvolvedor(id);
			redirectAttributes.addFlashAttribute("mensagem", "Desenvolvedor excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir! " + e.getMessage());
		}
		
		return mv;
		
	}
}
