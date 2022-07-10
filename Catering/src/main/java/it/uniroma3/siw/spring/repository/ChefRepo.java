package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.spring.model.Chef;

@Component
public interface ChefRepo extends CrudRepository<Chef, Long>{

	boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);

}
