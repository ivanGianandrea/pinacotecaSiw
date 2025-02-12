package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {

@Autowired
UserRepository userRepository;

public User findUser(Long id) {
	return userRepository.findById(id).get();
}

public User salvaUser(User user) {
	return userRepository.save(user);
}

}
