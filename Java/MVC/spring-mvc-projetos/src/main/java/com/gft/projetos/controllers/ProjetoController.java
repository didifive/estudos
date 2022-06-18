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
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoException;
import com.gft.projetos.exceptions.DesenvolvedorNaoEncontradoNoProjetoException;
import com.gft.projetos.exceptions.ProjetoNaoEncontradoException;
import com.gft.projetos.services.DesenvolvedorService;
import com.gft.projetos.services.LinguagemService;
import com.gft.projetos.services.ProjetoService;

@Controller
@RequestMapping("/projeto")
public class ProjetoController {
	
	//ModelAndView Objects
	protected static final String PROJETO = "projeto";
	protected static final String MENSAGEM = "mensagem";
	protected static final String LISTA = "lista";
	protected static final String LISTA_LINGUAGENS = "listaLinguagens";
	protected static final String LISTA_DESENVOLVEDORES = "listaDesenvolvedores";
	
	//Path and files Views of Projeto
	protected static final String FORM = PROJETO + "/form.html";
	protected static final String LISTAR = PROJETO + "/listar.html";
	protected static final String DETALHES = PROJETO + "/detalhes.html";
	
	//Messages
	protected static final String MESSAGE_SAVE_SUCCESS = "Projeto salvo com sucesso.";
	protected static final String MESSAGE_DELETE_SUCCESS = "Projeto excluído com sucesso.";
	protected static final String MESSAGE_DELETE_ERROR = "Erro ao excluir projeto! ";
	protected static final String MESSAGE_REMOVE_DESENVOLVEDOR_SUCCESS = "Desenvolvedor retirado com sucesso.";
	protected static final String MESSAGE_REMOVE_DESENVOLVEDOR_ERROR = "Erro ao retirar desenvolvedor! ";
	

	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private LinguagemService linguagemService;
	
	@Autowired
	private DesenvolvedorService desenvolvedorService;
	
	@GetMapping("/editar")
	public ModelAndView editarProjeto(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(FORM);
		
		obterProjeto(id, mv);
		
		adicionarListasExtras(mv);
		
		return mv;
	}
	
	
	@PostMapping("/editar")
	public ModelAndView salvarProjeto(
			@Valid Projeto projeto
			, BindingResult bindingResult
	){
		
		ModelAndView mv = new ModelAndView(FORM);
		
		if(bindingResult.hasErrors()) {
			mv.addObject(PROJETO, projeto);
			return mv;
		}
		
		projeto = salvarProjeto(projeto);
		
		obterProjeto(projeto.getId(), mv);
		
		adicionarListasExtras(mv, MESSAGE_SAVE_SUCCESS);
		
		return mv;
		
	}


	@GetMapping
	public ModelAndView listarProjetos(String nome, String apelido) {
		
		ModelAndView mv = new ModelAndView(LISTAR);
		
		mv.addObject(LISTA, projetoService.listarProjetos(nome,apelido));
		
		adicionarVariaveisBuscadas(nome, apelido, mv);
		
		return mv;
		
	}

	
	@GetMapping("/detalhes")
	public ModelAndView detalheProjeto(Long id) {
		
		ModelAndView mv = new ModelAndView(DETALHES);
		
		obterProjeto(id, mv);
		
		return mv;
		
	}


	@GetMapping("/excluir")
	public ModelAndView excluirProjeto(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/"+PROJETO);
		
		try {
			projetoService.excluirProjeto(id);
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_SUCCESS);
		} catch (ProjetoNaoEncontradoException e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_DELETE_ERROR + e.getMessage());
		}
		
		return mv;
		
	}
	
	@GetMapping("/detalhes/remover-desenvolvedor")
	public ModelAndView removerDesenvolvedorDoProjeto(
			@RequestParam Long idProjeto
			, @RequestParam Long idDesenvolvedor
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView("redirect:/" + PROJETO + "?id=" + idProjeto);
		
		try {
			projetoService.removerDesenvolvedorDoProjeto(idProjeto, idDesenvolvedor);
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_REMOVE_DESENVOLVEDOR_SUCCESS);
		} catch (ProjetoNaoEncontradoException
							| DesenvolvedorNaoEncontradoException
							| DesenvolvedorNaoEncontradoNoProjetoException e) {
			redirectAttributes.addFlashAttribute(MENSAGEM, MESSAGE_REMOVE_DESENVOLVEDOR_ERROR + e.getMessage());
		}
		
		return mv;
		
	}
	
	private void adicionarListasExtras(ModelAndView mv) {
		adicionarListasExtras(mv, "");
	}
	
	private void adicionarListasExtras(ModelAndView mv, String mensagem) {
		
		mv.addObject(LISTA_DESENVOLVEDORES,desenvolvedorService.listarTodosDesenvolvedores());
		mv.addObject(LISTA_LINGUAGENS,linguagemService.listarLinguagens());
		
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
  		} catch (ProjetoNaoEncontradoException e) {
  			projeto = new Projeto();
  			mv.addObject(MENSAGEM,e.getMessage());
  		}
		}
		
		mv.addObject(PROJETO, projeto);

	}
	
	private Projeto salvarProjeto(Projeto projeto) {
		
		projeto.setApelido(projeto.getApelido().toUpperCase());
		
		return projetoService.salvarProjeto(projeto);
	
	}
	
	private void adicionarVariaveisBuscadas(String nome, String apelido, ModelAndView mv) {
		
		mv.addObject("nome",nome);
		mv.addObject("apelido",apelido);
		
	}

}
