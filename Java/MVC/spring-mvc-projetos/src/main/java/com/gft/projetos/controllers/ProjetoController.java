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
	
	private final String PROJETO = "projeto";
	private final String MENSAGEM = "mensagem";

	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@GetMapping("/editar")
	public ModelAndView editarProjeto(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("projeto/form.html");
		
		obterProjeto(id, mv);
		
		adicionarListasExtras(mv);
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarProjeto(
			@Valid Projeto projeto
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView("projeto/form.html");
		
		if(bindingResult.hasErrors()) {
			mv.addObject("projeto", projeto);
			return mv;
		}
		
		salvarProjeto(projeto);
		
		obterProjeto(projeto.getId(), mv);
		
		adicionarListasExtras(mv,"Projeto salvo com sucesso.");
		
		return mv;
		
	}


	@GetMapping
	public ModelAndView listarProjetos(String nome, String apelido) {
		
		ModelAndView mv = new ModelAndView("projeto/listar.html");
		
		mv.addObject("lista", projetoService.listarProjetos(nome,apelido));
		
		adicionarVariaveisBuscadas(nome, apelido, mv);
		
		return mv;
		
	}

	
	@GetMapping("/detalhes")
	public ModelAndView detalheProjeto(Long id) {
		
		ModelAndView mv = new ModelAndView("projeto/detalhes.html");
		
		obterProjeto(id, mv);
		
		return mv;
		
	}


	@GetMapping("/excluir")
	public ModelAndView excluirProjeto(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/projeto");
		
		try {
			projetoService.excluirProjeto(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, "Projeto excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, "Erro ao excluir! " + e.getMessage());
		}
		
		return mv;
		
	}
	
	@GetMapping("/detalhes/excluirDesenvolvedor")
	public ModelAndView retirarDesenvolvedorDoProjeto(
			@RequestParam Long idProjeto
			, @RequestParam Long idDesenvolvedor
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/projeto/detalhes?id="+idProjeto);
		
		try {
			projetoService.retirarDesenvolvedorDoProjeto(idProjeto, idDesenvolvedor);
			redirectAttributes.addFlashAttribute(MENSAGEM, "Desenvolvedor retirado com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, "Erro ao retirar desenvolvedor! " + e.getMessage());
		}
		
		return mv;
		
	}
	
	private void adicionarListasExtras(ModelAndView mv) {
		adicionarListasExtras(mv, "");
	}
	
	private void adicionarListasExtras(ModelAndView mv, String mensagem) {
		
		mv.addObject("listaDesenvolvedores",desenvolvedorService.listarTodosDesenvolvedores());
		mv.addObject("listaLinguagens",linguagemService.listarLinguagens());
		
		if(!mensagem.isBlank())
			mv.addObject(MENSAGEM,mensagem);
	}

	private void obterProjeto(Long id, ModelAndView mv) {
		
		Projeto projeto;
		
		if(id == null) {
			projeto = new Projeto();
		} else {
  		try {
  			projeto = projetoService.obterProjeto(id);
  		} catch (Exception e) {
  			projeto = new Projeto();
  			mv.addObject(MENSAGEM,e.getMessage());
  		}
		}
		
		mv.addObject(PROJETO, projeto);

	}
	
	private void salvarProjeto(Projeto projeto) {
		
		projeto.setApelido(projeto.getApelido().toUpperCase());
		
		projetoService.salvarProjeto(projeto);
	
	}
	
	private void adicionarVariaveisBuscadas(String nome, String apelido, ModelAndView mv) {
		
		mv.addObject("nome",nome);
		mv.addObject("apelido",apelido);
		
	}

}
