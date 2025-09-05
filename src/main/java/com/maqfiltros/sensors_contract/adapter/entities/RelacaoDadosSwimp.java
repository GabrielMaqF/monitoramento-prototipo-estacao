package com.maqfiltros.sensors_contract.adapter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RelacaoDadosSwimp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long idEscola;
	private Integer idEquipamento;
	private String idSensor, idDispositivo;
	
	@Column(unique = true)
	private String idSwimp;
	
	public RelacaoDadosSwimp(Long idEscola, Integer idEquipamento, String idSensor, String idDispositivo) {
		this.idEscola = idEscola;
		this.idEquipamento = idEquipamento;
		this.idSensor = idSensor;
		this.idDispositivo = idDispositivo;
		this.idSwimp = idDispositivo + "_" + idEquipamento;
	}
	
//	public void setIdSwimp() {
//		this.idSwimp = (idDispositivo + "_" + idEquipamento);
//	}
}
