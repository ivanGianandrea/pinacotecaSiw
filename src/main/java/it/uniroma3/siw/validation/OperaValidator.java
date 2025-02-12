package it.uniroma3.siw.validation;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.OperaService;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;

@Component
public class OperaValidator implements Validator {

	@Autowired OperaService operaService;
	
@Override
public void validate(Object o, Errors errors) {
	Opera opera = (Opera)o;
	if (opera.getTitolo()!= null 
			 && operaService.operaEsistente(opera.getTitolo(),opera.getAnnodiRealizzazione(),opera.getTecnica())) {
errors.reject("opera.duplicata");
	 }	
}

@Override
public boolean supports(Class<?> aClass) {
	return Opera.class.equals(aClass);
}
	
	
}
