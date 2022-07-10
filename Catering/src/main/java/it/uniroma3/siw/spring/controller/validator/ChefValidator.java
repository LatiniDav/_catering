package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.ChefService;

@Component
public class ChefValidator implements Validator{

	@Autowired
	public ChefService chefService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	//controllo se lo chef passato è già presente
	@Override
	public void validate(Object target, Errors errors) {
		if(this.chefService.alreadyExists((Chef)target)) {
			errors.reject("chef.duplicato");
		}
	}

}
