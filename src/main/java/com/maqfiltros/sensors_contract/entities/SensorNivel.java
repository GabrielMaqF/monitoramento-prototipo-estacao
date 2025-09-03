package com.maqfiltros.sensors_contract.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.maqfiltros.sensors_contract.enums.TipoReservatorio;
import com.maqfiltros.sensors_contract.enums.TipoSensor;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SensorNivel")
@JsonTypeName("SensorNivel")
@Getter
@Setter
@NoArgsConstructor
public class SensorNivel extends Sensor {
	private static final long serialVersionUID = 1L;

	private Double alturaTotalReservatorioCm, alturaSensorCm, medida1, medida2, capacidade;

	@Enumerated(EnumType.STRING)
	private TipoReservatorio tipoReservatorio;

	// Construtor personalizado
	public SensorNivel(String descricao, Equipamento equipamento, Double alturaTotalReservatorioCm,
			Double alturaSensorCm, Double medida1, Double medida2, Double capacidade,
			TipoReservatorio tipoReservatorio) {
		super(null, descricao, equipamento);
		this.alturaTotalReservatorioCm = alturaTotalReservatorioCm;
		this.alturaSensorCm = alturaSensorCm;
		this.medida1 = medida1;
		this.medida2 = medida2;
		this.capacidade = capacidade;
		this.tipoReservatorio = tipoReservatorio;
	}

	@Override
	public String getUnidadeMedida() {
		return "Centimetros";
	}

	@Override
	public boolean leituraRecebida(String res) throws NumberFormatException {
		if (res != null && !res.isEmpty()) {
			double centimetros = Double.parseDouble(res);
			return centimetros > 0 && centimetros < 600;
		}
		return false;
	}

	@Override
	public TipoSensor getTipoSensor() {
		return TipoSensor.SensorNivel;
	}
}

//package com.maqfiltros.sensors_contract.entities;
//
//import com.fasterxml.jackson.annotation.JsonTypeName;
//import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
//import com.maqfiltros.sensors_contract.enums.TipoReservatorio;
//
//import jakarta.persistence.DiscriminatorValue;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//
//@Entity
//@DiscriminatorValue("SensorNivel")
//@JsonTypeName("SensorNivel")
//public class SensorNivel extends Equipamento {
//	private static final long serialVersionUID = 1L;
//
//	private Double alturaTotalReservatorioCm, alturaEquipamentoCm, medida1, medida2, capacidade;
//
//	@Enumerated(EnumType.STRING) // Adicione esta anotação
//	private TipoReservatorio tipoReservatorio;
//
//	public SensorNivel() {
//	}
//
//	public SensorNivel(String modelo, String descricao, Escola escola, Double alturaTotalReservatorioCm,
//			Double alturaEquipamentoCm, Double medida1, Double medida2, Double capacidade,
//			TipoReservatorio tipoReservatorio) {
//		super(null, modelo, descricao, escola);
//		this.alturaTotalReservatorioCm = alturaTotalReservatorioCm;
//		this.alturaEquipamentoCm = alturaEquipamentoCm;
//		this.medida1 = medida1;
//		this.medida2 = medida2;
//		this.capacidade = capacidade;
//		this.tipoReservatorio = tipoReservatorio;
//	}
//
//	public Double getAlturaEquipamentoCm() {
//		return alturaEquipamentoCm;
//	}
//
//	public void setAlturaEquipamentoCm(Double alturaEquipamentoCm) {
//		this.alturaEquipamentoCm = alturaEquipamentoCm;
//	}
//
//	public Double getAlturaTotalReservatorioCm() {
//		return alturaTotalReservatorioCm;
//	}
//
//	public void setAlturaTotalReservatorioCm(Double alturaTotalReservatorioCm) {
//		this.alturaTotalReservatorioCm = alturaTotalReservatorioCm;
//	}
//
//	public Double getMedida1() {
//		return medida1;
//	}
//
//	public void setMedida1(Double medida1) {
//		this.medida1 = medida1;
//	}
//
//	public Double getMedida2() {
//		return medida2;
//	}
//
//	public void setMedida2(Double medida2) {
//		this.medida2 = medida2;
//	}
//
//	public Double getCapacidade() {
//		return capacidade;
//	}
//
//	public void setCapacidade(Double capacidade) {
//		this.capacidade = capacidade;
//	}
//
//	public TipoReservatorio getTipoReservatorio() {
//		return tipoReservatorio;
//	}
//
//	public void setTipoReservatorio(TipoReservatorio tipoReservatorio) {
//		this.tipoReservatorio = tipoReservatorio;
//	}
//
//	@Override
//	public String getUnidadeMedida() {
//		return "Centimetros";
//	}
//
//	@Override
//	public boolean leituraRecebida(String res) throws NumberFormatException {
//		if (res != null && !res.isEmpty()) {
//			double centimetros = Double.parseDouble(res);
//			if (centimetros > 0 && centimetros < 600) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public TipoEquipamento getTipoEquipamento() {
//		return TipoEquipamento.SensorNivel;
//	}
//
//}
