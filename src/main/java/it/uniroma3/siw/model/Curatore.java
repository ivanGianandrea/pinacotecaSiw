package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
public class Curatore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Size(min = 1)
	private String nome;
	@NotBlank
	private String cognome;
	@NotBlank
	private String codicefiscale;
	@NotNull
	private LocalDate dataDiNascita;
	@NotBlank
	private String luogoDiNascita;
	@OneToOne(mappedBy = "curatore")
	AreaPinacoteca areaPinacoteca;
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
	public String getCodicefiscale() {
		return codicefiscale;
	}
	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}
	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	
	public AreaPinacoteca getAreaPinacoteca() {
		return areaPinacoteca;
	}
	public void setAreaPinacoteca(AreaPinacoteca areaPinacoteca) {
		this.areaPinacoteca = areaPinacoteca;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codicefiscale);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curatore other = (Curatore) obj;
		return Objects.equals(codicefiscale, other.codicefiscale);
	}
	
	
}
