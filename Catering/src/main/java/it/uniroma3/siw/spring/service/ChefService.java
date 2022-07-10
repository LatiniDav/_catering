package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepo;

@Service
public class ChefService {

	@Autowired
	private ChefRepo chefRepo;

	public Chef getChefById(Long id) {
		return chefRepo.findById(id).get();
	}

	@Transactional
	public void save(@Valid Chef chef) {
		chefRepo.save(chef);
	}

	public boolean alreadyExists(Chef chef) {
		if(chefRepo.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita()))
			return true;
		return false;
	}

	public List<Chef> getAllChef() {
		List<Chef> chefs= new ArrayList<>();
		for(Chef chef: chefRepo.findAll()) {
			chefs.add(chef);
		}
		return chefs;
	}

	public void deleteChefById(Long id) {
		chefRepo.deleteById(id);
	}

	public List<Chef> findAll() {
		List<Chef> chefs= new ArrayList<>();
		for(Chef chef: chefRepo.findAll()) {
			chefs.add(chef);
		}
		return chefs;	
	}	
	
	
}
