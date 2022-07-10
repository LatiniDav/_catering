package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.spring.model.Piatto;

@Component
public interface PiattoRepo extends CrudRepository<Piatto,Long>{

	boolean existsByNome(String nome);
	
}
