package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import it.uniroma3.siw.model.AreaPinacoteca;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.AreaPinacotecaService;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.validation.OperaValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class OperaController {
	
@Autowired
public OperaService operaService;
@Autowired
public CuratoreService curatoreService;
@Autowired
public AreaPinacotecaService areaPinacotecaService;
@Autowired
private OperaValidator operaValidator;
@Autowired
private ArtistaService artistaService;


@GetMapping("/admin/gestioneOpere/opere")
public String ShowOpera(Model model) {
	List<Opera> opere = operaService.getallOperas();
	if(opere.isEmpty()) {
		model.addAttribute("message", "Non ci sono opere disponibili.");
}
	model.addAttribute("opere", opere);
	return"opere";
}


@GetMapping("/admin/gestioneOpere")
public String gestioneOpere() {
    return "gestioneOpere"; 
}


@GetMapping("/admin/gestioneOpere/formNewOpera")
public String formNewOpera(Model model) {
	model.addAttribute("opera",new Opera());
	return "formNewOpera";
}

@PostMapping("/admin/gestioneOpere/opere")
public String newOpera(@Valid @ModelAttribute("opera") Opera opera, BindingResult bindingResult, Model model) {
	this.operaValidator.validate(opera, bindingResult);
	 if(!bindingResult.hasErrors()) {
		 this.operaService.salvaOpera(opera);
			return "operaSalvata";
	 }
	
	else {
		return "formNewOpera";
	} 
	 
} 

@GetMapping("/admin/formUpdateOpera/{id}")
	public String aggiornaOpera(@PathVariable Long id, Model model){
	model.addAttribute("opera",operaService.findById(id).get());
	return "formUpdateOpera";
}

@PostMapping("/admin/formUpdateOpera/{id}/modificaTitolo")
    public String aggiornaTitoloOpera(@PathVariable("id") Long id,@RequestParam String titolo,Model model ) {
	Opera opera = operaService.findById(id).get();
	opera.setTitolo(titolo);
	operaService.salvaOpera(opera);
	model.addAttribute("opera",opera);
		return "formUpdateOpera";	
}

@PostMapping("/admin/formUpdateOpera/{id}/modificaAnno")
public String aggiornaAnnoOpera(@PathVariable("id") Long id,@RequestParam Integer annodiRealizzazione,Model model ) {
Opera opera = operaService.findById(id).get();
opera.setAnnodiRealizzazione(annodiRealizzazione);
operaService.salvaOpera(opera);
model.addAttribute("opera",opera);
	return "formUpdateOpera";	
}

@PostMapping("/admin/formUpdateOpera/{id}/modificaTecnica")
public String aggiornaTecnicaOpera(@PathVariable("id") Long id,@RequestParam String tecnica,Model model ) {
Opera opera = operaService.findById(id).get();
opera.setTecnica(tecnica);
operaService.salvaOpera(opera);
model.addAttribute("opera",opera);
	return "formUpdateOpera";	
}


@GetMapping("/admin/addAreatoOpera/{idOpera}")
    public String modificaAreatoOpera(@PathVariable("idOpera") Long id,Model model) {
	Opera opera = operaService.findById(id).get();
	List<AreaPinacoteca> aree = areaPinacotecaService.getAreeDisponibiliOpera(opera);
	AreaPinacoteca areaOpera = opera.getAreapinacoteca();
	if(aree.isEmpty()) {
		model.addAttribute("messaggioreErrore","Non ci Sono Aree");
	}
	else {
	model.addAttribute("aree",aree);
	model.addAttribute("opera",opera);
	model.addAttribute("areaOpera",areaOpera);
	}
	return "aggiungiAreatoOpera";
}

@Transactional
@PostMapping("/admin/addAreatoOpera/{idOpera}/{idArea}")
    public String setAreatoOpera(@PathVariable("idOpera") Long idOpera,@PathVariable("idArea") Long idArea, Model model ) {
	Opera opera = operaService.findById(idOpera).get();
	AreaPinacoteca area = areaPinacotecaService.findById(idArea).get();
	opera.setAreapinacoteca(area);
	operaService.salvaOpera(opera);
	areaPinacotecaService.aggiungiNuovaOpera(area, opera);
	areaPinacotecaService.salvaArea(area);
	model.addAttribute("aree",areaPinacotecaService.getAreeDisponibiliOpera(opera));
    model.addAttribute("opera",opera);
    model.addAttribute("areaOpera",opera.getAreapinacoteca());
	return "aggiungiAreatoOpera";
}

@Transactional
@PostMapping("/admin/deleteAreatoOpera/{idOpera}/{idArea}")
public String deleteAreatoOpera(@PathVariable("idOpera") Long idOpera,@PathVariable("idArea") Long idArea, Model model ) {
Opera opera = operaService.findById(idOpera).get();
AreaPinacoteca area = areaPinacotecaService.findById(idArea).get();
operaService.eliminaCollegamentoOperaArea(opera);
areaPinacotecaService.rimuoviCollegamentoAreatoOpera(area, opera);
model.addAttribute("aree",areaPinacotecaService.getAreeDisponibiliOpera(opera));
model.addAttribute("opera",opera);
model.addAttribute("areaOpera",null);
return "aggiungiAreatoOpera";
}

@GetMapping("/admin/addArtistatoOpera/{idOpera}")
public String aggiungiArtistatoOpera(@PathVariable("idOpera") Long id,Model model) {
Opera opera = operaService.findById(id).get();
List<Artista> artisti = artistaService.artistiDisponibili(opera);
Artista artista = opera.getArtista();
if(artisti.isEmpty()) {
	model.addAttribute("messaggioreErrore","Non ci Sono Artisti");
}
else {
model.addAttribute("artistiDisponibili",artisti);
model.addAttribute("opera",opera);
model.addAttribute("operaArtista",artista);
}
return "aggiungiArtistatoOpera";
}

@Transactional
@PostMapping("/admin/deleteArtistaFromOpera/{idOpera}/{idArtista}")
public String eliminaArtistafromOpera(@PathVariable Long idOpera, @PathVariable Long idArtista, Model model) {
	Artista artista = artistaService.findArtista(idArtista).get();
	Opera opera = operaService.findById(idOpera).get();
	operaService.eliminaCollegamentoOperaArtista(opera);
	artistaService.eliminaCollegamentoArtistaOpera(artista,opera);
	model.addAttribute("artistiDisponibili", artistaService.artistiDisponibili(opera));
	model.addAttribute("opera", opera);
	model.addAttribute("operaArtista", null);
	return "aggiungiArtistatoOpera";
}

@Transactional
@PostMapping("/admin/addArtistatoOpera/{idOpera}/{idArtista}")
    public String setArtistatoOpera(@PathVariable Long idOpera,@PathVariable("idArtista") Long idArtista, Model model ) {
	Opera opera = operaService.findById(idOpera).get();
	Artista artista = artistaService.findArtista(idArtista).get();
	operaService.aggiungiArtistatoOpera(opera, artista);
	artistaService.aggiungiOperatoArtista(artista, opera);
	model.addAttribute("artistiDisponibili",artistaService.artistiDisponibili(opera));
    model.addAttribute("opera",opera);
    model.addAttribute("operaArtista",artista);
	return "aggiungiArtistatoOpera";
}

//---------------------------



@GetMapping("/elencoOpere")
public String elencoOpere(Model model) {
	List<Opera> opere = operaService.getallOperas();
	model.addAttribute("opere", opere);
	return "elencoOpere";
}

@GetMapping("/elencoOpere/cercaOpere")
public String showFormRicercaOpere() {
    return "cercaOpere"; 
}

@GetMapping("/elencoOpere/cercaOpere/anno")
public String getOperasByAnno(@RequestParam Integer annodiRealizzazione, Model model) {
    List<Opera> opere = operaService.findByAnno(annodiRealizzazione);
    if (opere.isEmpty()) {
        model.addAttribute("message", "Nessuna opera trovata per l'anno " + annodiRealizzazione);
    }
    model.addAttribute("opere", opere);
    return "operaTrovata";
}

@GetMapping("/elencoOpere/cercaOpere/tecnica")
public String getOperasByTecnica(@RequestParam String tecnica, Model model) {
    List<Opera> opere = operaService.findbyTecnica(tecnica);
    if (opere.isEmpty()) {
        model.addAttribute("message", "Nessuna opera trovata per la tecnica " + tecnica);
    }
    model.addAttribute("opere", opere);
    return "operaTrovata";
}

@GetMapping("/elencoOpere/cercaOpere/artista")
public String getOperaByArtista(@RequestParam String nomeArtista,@RequestParam String cognomeArtista, Model model) {
    List<Opera> opere = operaService.cercaOperaPerArtista(nomeArtista,cognomeArtista);
    if (opere.isEmpty()) {
        model.addAttribute("message", "Nessuna opera trovata per l'artista " + nomeArtista + ' ' + cognomeArtista);
    }
    model.addAttribute("opere", opere);
    return "operaTrovata";
}

}



