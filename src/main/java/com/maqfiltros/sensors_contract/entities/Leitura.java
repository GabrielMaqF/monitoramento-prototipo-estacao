package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
//import com.maqfiltros.sensors_contract.services.exceptions.InvalidValueException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Leitura implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
    private String valor;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;
    
    public Leitura() {
    	
    }

	public Leitura(Long id, Instant moment , String valor, Equipamento equipamento){
		super();		
		this.id = id;
		this.moment = moment;
		this.equipamento = equipamento;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leitura other = (Leitura) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Leitura [id=" + id + ", dataHora=" + moment + ", valor=" + valor + ", unidade=" //+ unidade 
				+ ", equipamento=" + equipamento + "]";
	}
}
