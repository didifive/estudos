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

import com.gft.projetos.entities.Projeto;
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;
import com.gft.projetos.services.ProjetoService;

@Controller
@RequestMapping("/projeto")
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@GetMapping("/editar")
	public ModelAndView editarProjeto(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("projeto/form.html");
		
		Projeto projeto;
		
		if(id==null) {
			projeto = new Projeto();
		} else {
			projeto = buscaProjeto(mv, id);
		}
		
		adicionarObjetosMV(mv, projeto);
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarProjeto(@Valid Projeto projeto, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("projeto/form.html");
		
		boolean novo = true;
		
		if(projeto.getId() != null)
			novo = false;
		
		if(bindingResult.hasErrors()) {
			mv.addObject("projeto", projeto);
			return mv;
		}
		
		projeto.setApelido(projeto.getApelido().toUpperCase());
		projetoService.salvarProjeto(projeto);
		
		if(novo) {
			projeto = new Projeto();
		}
		
		adicionarObjetosMV(mv, projeto,"Projeto salvo com sucesso.");
		
		return mv;
		
	}


	@GetMapping
	public ModelAndView listarProjetos(String nome, String apelido) {
		
		ModelAndView mv = new ModelAndView("projeto/listar.html");
		
		mv.addObject("lista", projetoService.listarProjetos(nome,apelido));
		
		mv.addObject("nome",nome);
		mv.addObject("apelido",apelido);
		
		
		return mv;
	}
	
	@GetMapping("/detalhes")
	public ModelAndView detalheProjeto(Long id) {
		
		ModelAndView mv = new ModelAndView("projeto/detalhes.html");
		
		Projeto projeto;
		
		projeto = buscaProjeto(mv, id);
		
		adicionarObjetosMV(mv, projeto);
		
		return mv;
	}


	@GetMapping("/excluir")
	public ModelAndView excluirProjeto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/projeto");
		
		try {
			projetoService.excluirProjeto(id);
			redirectAttributes.addFlashAttribute("mensagem", "Projeto excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir! " + e.getMessage());
		}
		
		return mv;
		
	}
	
	@GetMapping("/detalhes/excluirDesenvolvedor")
	public ModelAndView retirarDesenvolvedorDoProjeto(@RequestParam Long idProjeto, @RequestParam Long idDesenvolvedor, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/projeto/detalhes?id="+idProjeto);
		
		try {
			projetoService.retirarDesenvolvedorDoProjeto(idProjeto, idDesenvolvedor);
			redirectAttributes.addFlashAttribute("mensagem", "Desenvolvedor retirado com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao retirar desenvolvedor! " + e.getMessage());
		}
		
		return mv;
		
	}
	
	private void adicionarObjetosMV(ModelAndView mv, Projeto projeto) {
		adicionarObjetosMV(mv, projeto, "");
	}
	
	private void adicionarObjetosMV(ModelAndView mv, Projeto projeto, String mensagem) {
		mv.addObject("projeto", projeto);
		mv.addObject("listaDesenvolvedores",desenvolvedorService.listarTodosDesenvolvedores());
		mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
		if(!mensagem.isBlank())
			mv.addObject("mensagem",mensagem);
	}

	private Projeto buscaProjeto(ModelAndView mv, Long id) {
		Projeto projeto;
		try {
			projeto = projetoService.obterProjeto(id);
		} catch (Exception e) {
			projeto = new Projeto();
			mv.addObject("mensagem",e.getMessage());
		}
		return projeto;
	}

}
