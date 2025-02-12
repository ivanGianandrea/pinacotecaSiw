package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
@Autowired
CredentialsRepository credentialsRepository;

public Credentials findCredentialsbyId(Long id) {
	return credentialsRepository.findById(id).get();
}
public Credentials findCredentialsbyUsername(String Username) {
	return credentialsRepository.findByUsername(Username).get();
}

public Credentials saveCredentials(Credentials credentials) {
	return credentialsRepository.save(credentials);
	
}

public boolean utenteEsistente(String username) {
	return this.credentialsRepository.existsByUsername(username);
}

}
