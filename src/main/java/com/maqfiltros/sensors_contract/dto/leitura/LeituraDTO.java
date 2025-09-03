package com.maqfiltros.sensors_contract.dto.leitura;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.entities.Sensor;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class LeituraDTO {
	private Long id;
	private Instant moment;
	private Double valor;

	public LeituraDTO(Long id, Instant moment, Double valor) {
		this.id = id;
		this.moment = moment;
		this.valor = valor;
	}

	public LeituraDTO(Leitura leitura, Sensor sensor) {
		this.id = leitura.getId();
		this.moment = leitura.getMoment();

		switch (sensor.getTipoSensor()) {
		case Hidrometro: {
			this.valor = converterValorHidrometro(leitura.getValor(),
					(double) ((Hidrometro) sensor).getPulsosPorLitro());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + sensor.getTipoSensor());
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

}
