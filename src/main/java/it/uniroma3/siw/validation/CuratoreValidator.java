package it.uniroma3.siw.validation;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.service.OperaService;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;
@Component
public class CuratoreValidator implements Validator {
	@Autowired CuratoreService curatoreService;
	@Override
	public void validate(Object o, Errors errors) {
		Curatore curatore = (Curatore)o;
		
		if (curatore.getCodicefiscale()!= null 
				 && curatoreService.curatoreEsistente(curatore.getCodicefiscale())) {
	errors.reject("curatore.duplicato");
		 }	
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Curatore.class.equals(aClass);
	}

}
