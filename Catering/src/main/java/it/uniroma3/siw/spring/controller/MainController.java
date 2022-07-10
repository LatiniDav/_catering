package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.ChefService;

@Controller
public class MainController {
	
	@Autowired
	private ChefService chefService;
		
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
		List<Chef> chefs=this.chefService.findAll();
		model.addAttribute("chefs", chefs);
		return "index.html";
	}
	
	@RequestMapping(value = "/admin/indexAdmin", method = RequestMethod.GET) 
	public String showLoginRegistration (Model model) {
		return "indexAdmin.html";
	}	
	
}