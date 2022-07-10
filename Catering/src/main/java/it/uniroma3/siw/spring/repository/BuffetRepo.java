package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;

@Component
public interface BuffetRepo extends CrudRepository<Buffet,Long>{

	List<Buffet> findAllByChef(Chef chef);

	boolean existsByNomeAndDescrizione(String nome, String descrizione); 
	
	
}
