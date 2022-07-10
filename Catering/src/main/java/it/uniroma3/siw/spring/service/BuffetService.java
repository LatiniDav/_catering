package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.BuffetRepo;

@Service
public class BuffetService {
	
	@Autowired
	BuffetRepo buffetRepo;
	
	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<>();
		//findAll ritorna un iterator dunque utilizzo un for per salvarmi i dati in una lista
		for(Buffet b: buffetRepo.findAll()){
			buffets.add(b);
		}	
		return buffets;	
	}

	public Buffet findById(Long id) {
		return buffetRepo.findById(id).get();
	}

	public List<Buffet> findAllBuffetByChef(Chef chef) {
		return buffetRepo.findAllByChef(chef);
	}

	public boolean alreadyExists(Buffet buffet) {
		if(buffetRepo.existsByNomeAndDescrizione(buffet.getNome(),buffet.getDescrizione()))
			return true;
		return false;
	}

	@Transactional
	public void save(Buffet buffet) {
		buffetRepo.save(buffet);
	}

	public void deleteBuffetById(Long id) {
		buffetRepo.deleteById(id);
		
	}
	
}
