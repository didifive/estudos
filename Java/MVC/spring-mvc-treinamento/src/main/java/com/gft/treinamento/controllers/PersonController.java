package com.gft.treinamento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.treinamento.entities.Person;
import com.gft.treinamento.services.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@RequestMapping(method = RequestMethod.GET, path="/get")
	public ModelAndView getPerson(@RequestParam Long id){
		
		ModelAndView mv = new ModelAndView("person.html");
		
		try{
			Person person = personService.getPerson(id);
			
			mv.addObject("person", person);
		
		} catch(Exception e) {
			mv.addObject("message",e.getMessage());
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/new")
	public ModelAndView formPerson() {
		
		ModelAndView mv = new ModelAndView("formperson.html");
		mv.addObject("person", new Person());
		
		
		return mv;

	}
	
	@RequestMapping(method = RequestMethod.GET, path="/edit")
	public ModelAndView editPerson(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView("formperson.html");
		
		try {
			Person person = personService.getPerson(id);
			mv.addObject("person", person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView newPerson(Person person, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/person");
		
		personService.savePerson(person);
		
		redirectAttributes.addFlashAttribute("message", "Pessoa salva com sucesso!");
		
		return mv;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/delete")
	public ModelAndView deletePerson(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/person");
		
		try {
			personService.deletePerson(id);
			redirectAttributes.addFlashAttribute("message","Pessoa excluída com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage());
		}
		
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listPerson(){
		
		ModelAndView mv = new ModelAndView("listperson.html");
		mv.addObject("list", personService.listPerson());
		
		return mv;
	}
}
