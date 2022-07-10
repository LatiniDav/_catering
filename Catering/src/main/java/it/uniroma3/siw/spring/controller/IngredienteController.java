package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.controller.validator.IngredienteValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.service.IngredienteService;
	
@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingService;
	
	@Autowired
	private IngredienteValidator ingValidator;
	
	
	@PostMapping("/toDeleteIngrediente/{id}")
	public String deleteIngrediente(@PathVariable("id") Long id, Model model) {
		this.ingService.deleteIngredienteById(id);
		List<Ingrediente> ingredienti = this.ingService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "ingrediente.html";
	}
	
	@GetMapping("/ingrediente/{id}")
	public String getIngredienteById(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente= this.ingService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
	}
	
	@GetMapping("/tuttiGliIngredienti")
	public String getAllIngredienti(Model model) {
		List<Ingrediente> ingredienti=this.ingService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "ingredientsAdmin.html";
	}

	@GetMapping("/ingredienteAdmin/id/{id}")
	public String getIngredienteAdminById(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente= this.ingService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingredienteAdmin.html";
	}
	
}
