package com.maqfiltros.sensors_contract.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.maqfiltros.sensors_contract.enums.TipoSensor;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SensorCorrente")
@JsonTypeName("SensorCorrente")
@Getter
@Setter
@NoArgsConstructor
public class SensorCorrente extends Sensor {
	private static final long serialVersionUID = 1L;

	private Double correnteDeFuncionamento;

	// Construtor personalizado
	public SensorCorrente(String descricao, Equipamento equipamento, Double correnteDeFuncionamento) {
		super(null, descricao, equipamento);
		this.correnteDeFuncionamento = correnteDeFuncionamento;
	}

	@Override
	public TipoSensor getTipoSensor() {
		return TipoSensor.SensorCorrente;
	}

	@Override
	public String getUnidadeMedida() {
		return "Amperes";
	}

	@Override
	public boolean leituraRecebida(String res) throws NumberFormatException {
		// Implementar a lógica de validação aqui
		if (res != null && !res.isEmpty()) {
			double amperes = Double.parseDouble(res);
			// Exemplo: a leitura é válida se for positiva
			return amperes >= 0;
		}
		return false;
	}
}
//package com.maqfiltros.sensors_contract.entities;
//
//import java.io.Serializable;
//
//import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
//@AllArgsConstructor
//public class SensorCorrente extends Equipamento implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	private Double correnteDeFuncionamento;
//
//	public Double getCorrenteDeFuncionamento() {
//		return correnteDeFuncionamento;
//	}
//
//	public void setCorrenteDeFuncionamento(Double correnteDeFuncionamento) {
//		this.correnteDeFuncionamento = correnteDeFuncionamento;
//	}
//
//	@Override
//	public TipoEquipamento getTipoEquipamento() {
//		// TODO Auto-generated method stub
//		return TipoEquipamento.SensorCorrente;
//	}
//
//	@Override
//	public String getUnidadeMedida() {
//		// TODO Auto-generated method stub
//		return "Amperes";
//	}
//
//	@Override
//	public boolean leituraRecebida(String res) throws NumberFormatException {
//
//		return false;
//	}
//
//}
