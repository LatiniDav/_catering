package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.repository.IngredienteRepo;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepo ingRepo;
	
	public boolean alreadyExists(Ingrediente target) {
		return this.ingRepo.existsById(target.getId());
	}

	public void deleteIngredienteById(Long id) {
		this.ingRepo.deleteById(id);
	}

	public List<Ingrediente> findAll() {
		List<Ingrediente> ingredienti= new ArrayList<>();
		for(Ingrediente i: this.ingRepo.findAll()) {
			ingredienti.add(i);
		}
		return ingredienti;
	}

	public Ingrediente findById(Long id) {
		return this.ingRepo.findById(id).get();
	}
}
