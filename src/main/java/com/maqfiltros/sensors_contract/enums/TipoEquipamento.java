package com.maqfiltros.sensors_contract.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum TipoEquipamento {

	BOMBA_ETE_1(1, "Bomba ETE 1"), BOMBA_ETE_2(2, "Bomba ETE 2"), BOMBA_LODO(3, "Bomba de Lodo"),
	BOMBA_SOPRADOR(4, "Bomba Soprador"), RESERVATORIO_ETE(90, "Reservatório da ETE");

	private final int codigo;
	private final String descricao;

	TipoEquipamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	/**
	 * Anotação @JsonValue Informa ao Jackson para usar o retorno deste método (o
	 * código inteiro) sempre que for serializar este enum para JSON.
	 */
	@JsonValue
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Anotação @JsonCreator Informa ao Jackson para usar este método estático para
	 * criar uma instância do enum a partir do valor recebido no JSON (o código
	 * inteiro).
	 */
	@JsonCreator
	public static TipoEquipamento fromCodigo(int codigo) {
		for (TipoEquipamento tipo : TipoEquipamento.values()) {
			if (tipo.getCodigo() == codigo) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Código de equipamento inválido: " + codigo);
	}
}