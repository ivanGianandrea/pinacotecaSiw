package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.validation.ArtistaValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
@Controller
public class ArtistaController {

	@Autowired
	ArtistaService artistaService;
	@Autowired
	ArtistaValidator artistaValidator;

	@GetMapping("/admin/gestioneArtisti")
	public String gestioneArtisti() {
		return "gestioneArtisti";
	}

	@GetMapping("/admin/gestioneArtisti/formNewArtista")
	public String formNuovoArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "formNewArtista";
	}

	@PostMapping("/admin/formNewArtista/artista")
	public String inserisciNuovoArtista(@Valid @ModelAttribute("artista") Artista artista, BindingResult bindingResult,
			Model model) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			artistaService.salvaArtista(artista);
			return "artistaSalvato";
		}
		return "formNewArtista";
	}

	@GetMapping("/admin/gestioneArtisti/artisti")
	public String modificheArtisti(Model model) {

		List<Artista> artisti = artistaService.getallArtisti();
		if (artisti.isEmpty()) {
			model.addAttribute("message", "Non ci sono opere disponibili.");
		}
		model.addAttribute("artisti", artisti);
		return "artisti";
	}

	@GetMapping("/admin/formUpdateArtista/{idArtista}")
	public String formUpdateArtista(@PathVariable ("idArtista") Long id, Model model) {
		Artista artista = artistaService.findArtista(id).get();
		model.addAttribute("artista", artista);
		return "formUpdateArtista";
	}

	
	@PostMapping("/admin/formUpdateArtista/{idArtista}/modificaNome")
	public String modificaNome(@PathVariable("idArtista") Long id, @RequestParam String nome,Model model) {
		/*if (nome == null || nome.trim().isEmpty()) {
	        model.addAttribute("error", "Il nome non può essere vuoto.");
	        return "formUpdateArtista";
	    }*/
		Artista artista =artistaService.findArtista(id).get();
		artista.setNome(nome);
		artistaService.salvaArtista(artista);
		model.addAttribute("artista",artista);
		return "formUpdateArtista";
}
	
	@PostMapping("/admin/formUpdateArtista/{idArtista}/modificaCognome")
	public String modificaCognome(@PathVariable("idArtista") Long id, @RequestParam String cognome,Model model) {
		/*if (cognome == null || cognome.trim().isEmpty()) {
        model.addAttribute("error", "Il cognome non può essere vuoto.");
        return "formUpdateArtista";
    }*/
		Artista artista = artistaService.findArtista(id).get();
		artista.setCognome(cognome);
		artistaService.salvaArtista(artista);
		model.addAttribute("artista",artista);
		return "formUpdateArtista";
}
	
	@PostMapping("/admin/formUpdateArtista/{idArtista}/modificaDataDiNascita")
	public String modificaDataDiNascita(@PathVariable("idArtista") Long id, @RequestParam("dataDiNascita") LocalDate dataDiNascita,Model model) {
		/*if (dataDiNascita == null) {
        model.addAttribute("error", "la data di nascita non può essere nulla.");
        return "formUpdateArtista";
    }*/
		Artista artista =artistaService.findArtista(id).get();
		artista.setDataDiNascita(dataDiNascita);
		artistaService.salvaArtista(artista);
		model.addAttribute("artista",artista);
		return "formUpdateArtista";
}
	
	@PostMapping("/admin/formUpdateArtista/{idArtista}/modificaDataDiMorte")
	public String modificaDataDiMorte(@PathVariable("idArtista") Long id, @RequestParam("dataDiMorte") LocalDate dataDiMorte,Model model) {
		/*if (dataDiMorte == null) {
        model.addAttribute("error", "la data di morte non può essere nulla.");
        return "formUpdateArtista";
    }*/
		Artista artista =artistaService.findArtista(id).get();
		artista.setDataDiMorte(dataDiMorte);
		artistaService.salvaArtista(artista);
		model.addAttribute("artista",artista);
		return "formUpdateArtista";
}
	@PostMapping("/admin/formUpdateArtista/{idArtista}/modificaLuogoDiNascita")
	public String modificaLuogoDiNascita(@PathVariable("idArtista") Long id, @RequestParam("luogoDiNascita") String luogoDiNascita,Model model) {
		/*if (luogoDiNascita == null || luogoDiNascita.trim().isEmpty()) {
        model.addAttribute("error", "Il luogo di nascita non può essere vuoto.");
        return "formUpdateArtista";
    }*/
		Artista artista =artistaService.findArtista(id).get();
		artista.setLuogoDiNascita(luogoDiNascita);
		artistaService.salvaArtista(artista);
		model.addAttribute("artista",artista);
		return "formUpdateArtista";
}
	
	
	//elimino l artista 
	@PostMapping("/admin/formUpdateArtista/{idArtista}/elimina")
	public String elimina(@PathVariable Long idArtista, Model model ) {
	    Artista artista = artistaService.findArtista(idArtista).get();
		artistaService.eliminazione(artista);
		return "artistaEliminato";
	}
}
