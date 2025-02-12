package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;
import it.uniroma3.siw.model.AreaPinacoteca;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.AreaPinacotecaRepository;
import jakarta.transaction.Transactional;

@Service
public class AreaPinacotecaService {

	@Autowired
	AreaPinacotecaRepository areaPinacotecaRepository;
	
	  public Optional<AreaPinacoteca> findById(Long id){
		  return areaPinacotecaRepository.findById(id);
	  }
		  
	  public List<AreaPinacoteca> getAllArea(){
		  return  (List<AreaPinacoteca>) areaPinacotecaRepository.findAll();
	  }
	  
	  public List<AreaPinacoteca> getAreeDisponibiliCuratore(){
		  return  areaPinacotecaRepository.findByCuratoreIsNull();
	  }
	  
	  public List<AreaPinacoteca> getAreeDisponibiliOpera(Opera opera){
		 List<AreaPinacoteca> tutte = (getAllArea());
		 AreaPinacoteca areaOccupata = opera.getAreapinacoteca();
		 tutte.remove(areaOccupata);
		 return tutte;
	  }
	  
	  public void aggiungiNuovaOpera(AreaPinacoteca areaPinacoteca, Opera opera) {
		  areaPinacoteca.getOpere().add(opera);
		  
	  }
	  public AreaPinacoteca salvaArea(AreaPinacoteca areaPinacoteca) {
		  return areaPinacotecaRepository.save(areaPinacoteca);
	  }
	  
	  @Transactional
		public void rimuoviCollegamentoAreatoCuratore(AreaPinacoteca areaPinacoteca) {

	                areaPinacoteca.setCuratore(null);  // Rimuovi il curatore dall area
	                areaPinacotecaRepository.save(areaPinacoteca); // Salva l'area aggiornata
	            }
	  @Transactional
		public void rimuoviCollegamentoAreatoOpera(AreaPinacoteca areaPinacoteca, Opera opera) {
                    List<Opera> opere = areaPinacoteca.getOpere();
                    opere.remove(opera);   // Rimuovi opera dall area
	                areaPinacoteca.setOpere(opere);   // aggiorno lista opere
	                areaPinacotecaRepository.save(areaPinacoteca); // Salva l'area aggiornata
	            }
}
