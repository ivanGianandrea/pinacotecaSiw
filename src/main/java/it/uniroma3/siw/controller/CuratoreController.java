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

import it.uniroma3.siw.model.AreaPinacoteca;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.AreaPinacotecaService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.validation.CuratoreValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDate;
@Controller
public class CuratoreController {
	@Autowired
	public CuratoreService curatoreService;
	@Autowired
	public AreaPinacotecaService areaPinacotecaService;
	@Autowired
	public CuratoreValidator curatoreValidator;
	
	@GetMapping("/admin/gestioneCuratori/curatori")
	public String showAllCuratori(Model model) {
		List<Curatore> curatori = curatoreService.getallCuratori();
		if(curatori.isEmpty()) {
			model.addAttribute("messaggio errore", "non ci sono curatori");
		}
		model.addAttribute("curatori", curatori );
		return "curatori";
	}
	
	@GetMapping("/admin/gestioneCuratori")
		public String gestioneCuratori(Model model) {
			return "gestioneCuratori";
		}
		
	
	
	@GetMapping("/admin/gestioneCuratori/formNewCuratore")
	public String formNewCuratore(Model model) {
		model.addAttribute("curatore",new Curatore());
		return "formNewCuratore";
	}

	@PostMapping("/admin/gestioneCuratori/nuovoCuratoreAggiunto")
	public String newCuratore(@Valid @ModelAttribute("curatore") Curatore curatore,BindingResult bindingResult ,Model model) {
		this.curatoreValidator.validate(curatore, bindingResult);
		if(!bindingResult.hasErrors()){
			this.curatoreService.salvaCuratore(curatore);
			return "curatoreSalvato";
		}
		else {
			return "formNewCuratore";
		} 
	} 
	
	@GetMapping("/admin/formUpdateCuratore/{idCuratore}")
		public String aggiornaCuratore(@PathVariable("idCuratore") Long id, Model model) {
		       model.addAttribute("curatore",curatoreService.findById(id).get());
		       return "formUpdateCuratore";
	}
	
	@GetMapping("/admin/formUpdateCuratori/{idCuratore}/addAreatoCuratore")
	public String aggiungiAreatoCuratore(@PathVariable("idCuratore") Long id,Model model) {
		List<AreaPinacoteca> areeDisponibili = areaPinacotecaService.getAreeDisponibiliCuratore();
		Curatore curatore = curatoreService.findById(id).get();
		AreaPinacoteca areaCuratore = curatore.getAreaPinacoteca();
		if(areeDisponibili.isEmpty()) {
			model.addAttribute("messaggioreErrore","Non ci Sono Aree");
		}
		else {
			
			model.addAttribute("areePinacotecaDisponibili",areeDisponibili);
			model.addAttribute("curatore",curatore);
			model.addAttribute("areaCuratore", areaCuratore);
		}
		return "aggiungiAreatoCuratore";
	}
	
	
	@Transactional
	@PostMapping("/admin/deleteAreaFromCuratore/{idCuratore}/{idArea}")
	public String deleteAreaFromCuratore(@PathVariable("idCuratore") Long idCuratore,@PathVariable("idArea") Long idArea, Model model){
	    AreaPinacoteca areaCuratore = areaPinacotecaService.findById(idArea).get();
	    Curatore curatore = curatoreService.findById(idCuratore).get();
	    areaPinacotecaService.rimuoviCollegamentoAreatoCuratore(areaCuratore);
	    curatoreService.rimuoviCollegamentoCuratoreArea(curatore);
	    areaPinacotecaService.salvaArea(areaCuratore);
	    curatoreService.salvaCuratore(curatore);
	    model.addAttribute("areePinacotecaDisponibili", areaPinacotecaService.getAreeDisponibiliCuratore());
	    model.addAttribute("curatore",curatore);
		model.addAttribute("areaCuratore", null);
	    return "aggiungiAreatoCuratore";
	}
	
	
	@Transactional
	@PostMapping("/admin/addAreatoCuratore/{idCuratore}/{idArea}")
    public String setAreatoCuratore(@PathVariable("idCuratore") Long idCuratore,@PathVariable("idArea") Long idArea, Model model ) {
	Curatore curatore = curatoreService.findById(idCuratore).get();
	AreaPinacoteca areaCuratore = areaPinacotecaService.findById(idArea).get();
	curatore.setAreaPinacoteca(areaCuratore);
	curatoreService.salvaCuratore(curatore);
	areaCuratore.setCuratore(curatore);
	areaPinacotecaService.salvaArea(areaCuratore);
	model.addAttribute("areePinacotecaDisponibili", areaPinacotecaService.getAreeDisponibiliCuratore());
    model.addAttribute("curatore",curatore);
	model.addAttribute("areaCuratore", areaCuratore);
	return "aggiungiAreatoCuratore";
}
	
	@PostMapping("/admin/formUpdateCuratore/{idCuratore}/modificaNome")
    public String aggiornaNomeCuratore(@PathVariable("idCuratore") Long id,@RequestParam String nome,Model model ) {
	Curatore curatore = curatoreService.findById(id).get();
	curatore.setNome(nome);
	curatoreService.salvaCuratore(curatore);
	model.addAttribute("curatore", curatore);
		return "formUpdateCuratore";	
}
	
	@PostMapping("/admin/formUpdateCuratore/{idCuratore}/modificaCognome")
    public String aggiornaCognomeCuratore(@PathVariable("idCuratore") Long id,@RequestParam String cognome,Model model ) {
	Curatore curatore = curatoreService.findById(id).get();
	curatore.setCognome(cognome);
	curatoreService.salvaCuratore(curatore);
	model.addAttribute("curatore", curatore);
		return "formUpdateCuratore";	
}
	
	@PostMapping("/admin/formUpdateCuratore/{idCuratore}/modificaCodiceFiscale")
    public String aggiornaCodicefiscaleCuratore(@PathVariable("idCuratore") Long id,@RequestParam String codicefiscale,Model model) {
	Curatore curatore = curatoreService.findById(id).get();
	curatore.setCodicefiscale(codicefiscale);
	curatoreService.salvaCuratore(curatore);
	model.addAttribute("curatore", curatore);
		return "formUpdateCuratore";	
}
	
	@PostMapping("/admin/formUpdateCuratore/{idCuratore}/modificaDataDiNascita")
    public String aggiornaDataDiNascitaCuratore(@PathVariable("idCuratore") Long id,@RequestParam("dataDiNascita") LocalDate datadinascita,Model model ) {
	Curatore curatore = curatoreService.findById(id).get();
	curatore.setDataDiNascita(datadinascita);
	curatoreService.salvaCuratore(curatore);
	model.addAttribute("curatore", curatore);
		return "formUpdateCuratore";	
}
	
	@PostMapping("/admin/formUpdateCuratore/{idCuratore}/modificaLuogoDiNascita")
    public String aggiornaLuogoDiNascitaCuratore(@PathVariable("idCuratore") Long id,@RequestParam("luogoDiNascita") String luogodinascita,Model model ) {
	Curatore curatore = curatoreService.findById(id).get();
	curatore.setLuogoDiNascita(luogodinascita);
	curatoreService.salvaCuratore(curatore);
	model.addAttribute("curatore", curatore);
		return "formUpdateCuratore";	
}
	
	
}
