package it.uniroma3.siw.validation;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.service.OperaService;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;

@Component
public class ArtistaValidator implements Validator{
	
	@Autowired ArtistaService artistaService;
	@Override
	public void validate(Object o, Errors errors) {
		Artista artista = (Artista)o;
		
		if (artista.getNome() != null && artista.getCognome()!=null && artistaService.artistaEsistente(artista.getNome(), artista.getCognome())) {
	errors.reject("artista.duplicato");
		 }	
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Artista.class.equals(aClass);
	}

}
