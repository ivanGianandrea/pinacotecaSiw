package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

public class CredentialsValidator implements Validator {
	@Autowired
	CredentialsService credentialsService;
	
	@Override
	public void validate(Object o, Errors errors) {
		Credentials credentials = (Credentials)o;
		if (credentialsService.utenteEsistente(credentials.getUsername())) {
	errors.reject("utente.duplicato");
		 }
		
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}
}
