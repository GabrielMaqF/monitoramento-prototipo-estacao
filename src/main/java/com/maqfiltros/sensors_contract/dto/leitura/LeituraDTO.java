package com.maqfiltros.sensors_contract.dto.leitura;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.entities.Leitura;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeituraDTO {
	private Long id;
	private Instant moment;
	private Double valor;

	public LeituraDTO(Leitura leitura, Equipamento equipamento) {
		this.id = leitura.getId();
		this.moment = leitura.getMoment();

		switch (equipamento.getTipoEquipamento()) {
		case Hidrometro: {
			this.valor = converterValorHidrometro(leitura.getValor(),
					(double) ((Hidrometro) equipamento).getPulsosPorLitro());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + equipamento.getTipoEquipamento());
		}
	}

	public LeituraDTO(Leitura leitura) {
		this.id = leitura.getId();
		this.moment = leitura.getMoment();
		this.valor = Double.valueOf(leitura.getValor());
	}
	
	public LeituraDTO(LeituraPorMinutoDTO leitura) {
		this.moment = leitura.getMoment();
		this.valor = leitura.getValor();
	}

	public LeituraDTO() {
	}

	private Double converterValorHidrometro(String valorString, Double pulsoPorLitro) {
		double valor = Double.parseDouble(valorString);
		return (valor * pulsoPorLitro) / 1000.0;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
