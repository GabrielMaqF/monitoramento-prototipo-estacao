package com.maqfiltros.sensors_contract.adapter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RelacaoDadosSwimp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long idEscola, idEquipamento;
	private String idSensor, idDispositivo, idSwimp;
	
	public void setIdSwimp() {
		this.idSwimp = (idDispositivo + "_" + idEquipamento);
	}
}
