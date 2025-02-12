package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Opera;
import java.util.List;
import java.util.Optional;
public interface OperaRepository extends CrudRepository<Opera,Long> {
	

    @Query("SELECT a FROM Opera a WHERE a.artista.nome = :nome AND a.artista.cognome = :cognome")
    List<Opera> findByArtistaNomeAndCognome(String nome, String cognome);

    boolean existsByTitoloAndAnnodiRealizzazioneAndTecnica(String titolo, Integer anno, String tecnica);
    public List<Opera> findByAnnodiRealizzazione(Integer Anno);
    public List<Opera> findByTecnica(String tecnica);
    public Optional<Opera> findById(Long id);
	
		
	
}
