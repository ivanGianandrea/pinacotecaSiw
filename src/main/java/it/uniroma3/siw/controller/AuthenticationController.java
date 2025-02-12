package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		Credentials credentials = new Credentials();
		User user = new User();
		credentials.setUser(user);
		model.addAttribute("credentials", credentials);
		model.addAttribute("user", user);
		return "registrazione";
	}

	@GetMapping("/success")
	public String successo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		System.out.println("Username dell'utente: " + username);
		Credentials credentials = credentialsService.findCredentialsbyUsername(username);
		System.out.println("Ruolo dell'utente: " + credentials.getRole());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "indexAdmin";
		}
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			return "index";
		}
		return "login";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute Credentials credentials, BindingResult bindingCredentials,
			Model model) {
		if (bindingCredentials.hasErrors()) {
			return "registrazione";
		}
		String encodedPassword = passwordEncoder.encode(credentials.getPassword());
		credentials.setPassword(encodedPassword);
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentialsService.saveCredentials(credentials);

		return "login";
	}

	@GetMapping("/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		} else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();
			Credentials credentials = credentialsService.findCredentialsbyUsername(username);
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "indexAdmin";
			}
		}
		return "index";
	}

}