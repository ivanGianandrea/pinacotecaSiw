package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.OperaRepository;
import jakarta.transaction.Transactional;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import java.util.List;
import java.util.Optional;

@Service
public class OperaService {
	
@Autowired
private OperaRepository operaRepository;

public List<Opera> findByAnno(Integer anno) {
	return operaRepository.findByAnnodiRealizzazione(anno);
}

public Optional<Opera> findById(Long id) {
	return operaRepository.findById(id);
}
public List<Opera> cercaOperaPerArtista(String nome,String cognome){
	return operaRepository.findByArtistaNomeAndCognome(nome, cognome);
}

public List<Opera> getallOperas(){
	return (List<Opera>) operaRepository.findAll();
}

public boolean operaEsistente(String titolo, Integer anno, String tecnica) {
	return operaRepository.existsByTitoloAndAnnodiRealizzazioneAndTecnica(titolo, anno, tecnica);
}

public Opera salvaOpera(Opera opera) {
	return operaRepository.save(opera);
} 

public List<Opera> findbyTecnica(String tecnica){
	return operaRepository.findByTecnica(tecnica);	
}

@Transactional
public void eliminaCollegamentoOperaArea(Opera opera) {
	opera.setAreapinacoteca(null);
	salvaOpera(opera);
}

@Transactional
public void eliminaCollegamentoOperaArtista(Opera opera) {
	opera.setArtista(null);
	salvaOpera(opera);
}

@Transactional
public void aggiungiArtistatoOpera(Opera opera, Artista artista) {
	opera.setArtista(artista);
	salvaOpera(opera);
}



/*
public List<Opera> findbyAnno(int anno){
	return operaRepository.findbyAnno(anno);	
}
public List<Opera> findbyArtista(Artista artista){
	return operaRepository.findbyArtista(artista);	
}
public List<Opera> findbyTecnica(String tecnica){
	return operaRepository.findbyTecnica(tecnica);	
}
*/
}
