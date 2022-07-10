package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.PiattoRepo;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepo piattoRepo;

	public List<Piatto> findAll() {
		List<Piatto> piatti= new ArrayList<>();
		for(Piatto piatto: piattoRepo.findAll())
			piatti.add(piatto);
		return piatti;
	}

	public Piatto findById(Long id) {
		return piattoRepo.findById(id).get();
	}

	public boolean alreadyExists(Piatto piatto) {
		return piattoRepo.existsByNome(piatto.getNome());
	}

	public void save(@Valid Piatto piatto) {
		this.piattoRepo.save(piatto);
		
	}

	public void deletePiattoById(Long id) {
		this.piattoRepo.deleteById(id);
		
	}

	
}
