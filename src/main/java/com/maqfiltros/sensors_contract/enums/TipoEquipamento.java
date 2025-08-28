package com.maqfiltros.sensors_contract.enums;

public enum TipoEquipamento {
	Hidrometro, SensorNivel;
	/*
	Hidrometro(1),
	SensorNivel(2);
	
	private int code;
	
	private TipoEquipamento(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static TipoEquipamento valueOf(int code) {
		for(TipoEquipamento value : TipoEquipamento.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid EquipamentoUnidadeMedida code!");
	}
	*/
}
