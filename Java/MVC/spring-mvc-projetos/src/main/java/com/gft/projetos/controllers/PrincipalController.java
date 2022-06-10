package com.gft.projetos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {
	
	@RequestMapping
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("index.html");
		
		return mv;
	}
	
	@RequestMapping("/sobre")
	public ModelAndView about() {
		
		ModelAndView mv = new ModelAndView("sobre.html");
		
		return mv;
	}

}
