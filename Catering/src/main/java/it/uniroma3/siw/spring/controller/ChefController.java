package it.uniroma3.siw.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.ChefService;

@Controller
public class ChefController {
	
	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;
	
	@GetMapping("/chefs")
	public String getAllChef(Model model) {
		List<Chef> chefs=chefService.getAllChef();
		model.addAttribute("chefs", chefs);
		return "chefs.html";
	}
	
	@GetMapping("/chefsAdmin")
	public String getAllChefForAdmin(Model model) {
		List<Chef> chefs=chefService.getAllChef();
		model.addAttribute("chefs", chefs);
		return "chefsAdmin.html";
	}		
	
	@GetMapping("/chef/id/{id}")
	public String getChefById(@PathVariable("id") Long id, Model model) {
		Chef chef=chefService.getChefById(id);
		model.addAttribute("chef",chef);
		return "chef.html";
	}
	
	@GetMapping("/chefAdmin/id/{id}")
	public String getChefAdminById(@PathVariable("id") Long id, Model model) {
		Chef chef=chefService.getChefById(id);
		model.addAttribute("chef",chef);
		return "chefAdmin.html";
	}
	
	@PostMapping("/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		chefValidator.validate(chef, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chef", chef);
			return this.getAllChefForAdmin(model);
		}
		return "chefFormAdmin.html";
	}
	
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model){
		chefService.deleteChefById(id);								//cancella prima lo chef dal db
		List<Chef> chefs=chefService.findAll();						//fatti dare tutti gli chef nel db
		model.addAttribute("chefs", chefs);
		return "chefsAdmin.html";
	}
	
	@RequestMapping(value="/chefFormAdmin", method=RequestMethod.GET)
	public String showChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "chefFormAdmin.html";
	}
}
