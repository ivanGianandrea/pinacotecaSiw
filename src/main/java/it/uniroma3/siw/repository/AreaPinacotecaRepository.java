package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.AreaPinacoteca;
import java.util.List;

public interface AreaPinacotecaRepository extends CrudRepository<AreaPinacoteca,Long>{

	List<AreaPinacoteca> findByCuratoreIsNull();
}
