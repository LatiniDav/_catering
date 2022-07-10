package it.uniroma3.siw.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.controller.validator.PiattoValidator;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;
	
	@GetMapping("/piatto")
	public String getAllPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "ingrediente.html";
	}
	
	@GetMapping("/piattoAdmin")
	public String getAllPiattiAdmin(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "piattiAdmin.html";
	}

	//se serve un attributo di piatto usa questo metodo e poi i relativi getter/setter
	@GetMapping("/piatto/{id}")
	public String getPiattoById(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "piatto.html";
	}

	//se serve un attributo di piatto usa questo metodo e poi i relativi getter/setter
		@GetMapping("/piatto/id/{id}")
		public String getPiattoAdminById(@PathVariable("id") Long id, Model model) {
			Piatto piatto = piattoService.findById(id);
			model.addAttribute("piatto", piatto);
			return "piattoAdmin.html";
		}
	
	@PostMapping("/addPiatto")
	public String addPiatto(@Valid @PathVariable("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		piattoValidator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("piatto", piatto);
			return "piatto.html";
		}
		return "piattoForm.html";
	}

	@PostMapping("/toDeletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		piattoService.deletePiattoById(id);
		List<Piatto> piatti= piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "piatto.html";
	}
	
	@GetMapping("/piattoFormAdmin")
	public String showPiattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		return "piattoFormAdmin.html";
	}
	
	
}
