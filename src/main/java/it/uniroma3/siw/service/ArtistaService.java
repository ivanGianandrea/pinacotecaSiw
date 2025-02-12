package it.uniroma3.siw.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.ArtistaRepository;
import it.uniroma3.siw.repository.OperaRepository;
import jakarta.transaction.Transactional;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {
	@Autowired
	private ArtistaRepository artistaRepository;

	public List<Artista> getallArtisti(){
		return (List<Artista>) artistaRepository.findAll();
	}

	public boolean artistaEsistente(String nome, String cognome) {
		return artistaRepository.existsByNomeAndCognome(nome, cognome);
	}

	public Artista salvaArtista(Artista artista) {
		return artistaRepository.save(artista);
	} 
	
	public Optional<Artista> findArtista(Long id){
		return artistaRepository.findById(id);
	}
	@Transactional
	public void eliminaCollegamentoArtistaOpera(Artista artista, Opera opera) {
		List<Opera> opere = artista.getOpere();
		opere.remove(opera);
		artista.setOpere(opere);
		salvaArtista(artista);
	}
	@Transactional
	public void aggiungiOperatoArtista(Artista artista, Opera opera) {
		List<Opera> opere = artista.getOpere();
		opere.add(opera);
		artista.setOpere(opere);
		salvaArtista(artista);
	}
	public List<Artista> artistiDisponibili(Opera opera){
		List<Artista> artisti = this.getallArtisti();
		Artista artista = opera.getArtista();
		artisti.remove(artista);
		return artisti;
	}
	
	public void eliminazione(Artista artista) {
		List<Opera> opere = artista.getOpere();
		for(int i = 0; i<opere.size();i++) {
			opere.get(i).setArtista(null);
		}
		artistaRepository.delete(artista);
	}

}
