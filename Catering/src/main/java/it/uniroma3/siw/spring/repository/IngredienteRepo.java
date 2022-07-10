package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.spring.model.Ingrediente;

@Component
public interface IngredienteRepo extends CrudRepository<Ingrediente,Long>{
	
	
}
