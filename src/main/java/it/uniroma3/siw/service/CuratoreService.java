package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.repository.CuratoreRepository;
import jakarta.transaction.Transactional;

@Service
public class CuratoreService {
	@Autowired
	private CuratoreRepository curatoreRepository;
	
	public Optional<Curatore> findById(Long id) {
		return curatoreRepository.findById(id);
	}
	
	public List<Curatore> getallCuratori(){
		return (List<Curatore>) curatoreRepository.findAll();
	}
	
	public boolean curatoreEsistente(String codicefiscale) {
		return curatoreRepository.existsByCodicefiscale(codicefiscale);
	}

	public Curatore salvaCuratore(Curatore curatore) {
		return curatoreRepository.save(curatore);
	} 
	
	@Transactional
	public void rimuoviCollegamentoCuratoreArea(Curatore curatore) {

                curatore.setAreaPinacoteca(null);  // Rimuovi l area dal curatore
                curatoreRepository.save(curatore); // Salva il curatore aggiornato
            }
        
    
}


