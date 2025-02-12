package it.uniroma3.siw.model;

import java.util.Objects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Opera {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@NotBlank
@Size(min = 1)
private String titolo;
@Min(value = 0)
private int annodiRealizzazione;
private String tecnica;
@ManyToOne
private Artista artista;
@ManyToOne
private AreaPinacoteca areapinacoteca;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}
public int getAnnodiRealizzazione() {
	return annodiRealizzazione;
}
public void setAnnodiRealizzazione(int annodiRealizzazione) {
	this.annodiRealizzazione = annodiRealizzazione;
}
public String getTecnica() {
	return tecnica;
}
public void setTecnica(String tecnica) {
	this.tecnica = tecnica;
}

public Artista getArtista() {
	return artista;
}
public void setArtista(Artista artista) {
	this.artista = artista;
}

public AreaPinacoteca getAreapinacoteca() {
	return areapinacoteca;
}
public void setAreapinacoteca(AreaPinacoteca areapinacoteca) {
	this.areapinacoteca = areapinacoteca;
}
@Override
public int hashCode() {
	return Objects.hash(annodiRealizzazione, artista, tecnica, titolo);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Opera other = (Opera) obj;
	return annodiRealizzazione == other.annodiRealizzazione 
			&& Objects.equals(tecnica, other.tecnica) && Objects.equals(titolo, other.titolo);
}


}
