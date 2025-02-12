package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Artista {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@NotBlank
private String nome;
@NotBlank
private String cognome;
@NotNull
private LocalDate dataDiNascita;
@NotBlank
private String luogoDiNascita;
private LocalDate dataDiMorte;
@OneToMany(mappedBy = "artista")
private List<Opera> opere;


public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}

public String getLuogoDiNascita() {
	return luogoDiNascita;
}
public void setLuogoDiNascita(String luogoDiNascita) {
	this.luogoDiNascita = luogoDiNascita;
}

public LocalDate getDataDiNascita() {
	return dataDiNascita;
}
public void setDataDiNascita(LocalDate dataDiNascita) {
	this.dataDiNascita = dataDiNascita;
}
public LocalDate getDataDiMorte() {
	return dataDiMorte;
}
public void setDataDiMorte(LocalDate dataDiMorte) {
	this.dataDiMorte = dataDiMorte;
}

public List<Opera> getOpere() {
	return opere;
}
public void setOpere(List<Opera> opere) {
	this.opere = opere;
}
@Override
public int hashCode() {
	return Objects.hash(cognome, nome);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Artista other = (Artista) obj;
	return Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome);
}


}
