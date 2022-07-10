package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.service.IngredienteService;


@Component
public class IngredienteValidator implements Validator{

	@Autowired
	private IngredienteService ingService;
	
	@Override
	public boolean supports(Class<?> pClass) {
		return Buffet.class.equals(pClass);
	}

	//controllo se l'ingrediente passato è già presente
	@Override
	public void validate(Object target, Errors errors) {
		if(this.ingService.alreadyExists((Ingrediente)target)) {
			errors.reject("ingrediente.duplicato");
		}
	}

	

}
