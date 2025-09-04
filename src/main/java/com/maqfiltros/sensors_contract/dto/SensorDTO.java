package com.maqfiltros.sensors_contract.dto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.maqfiltros.sensors_contract.dto.leitura.LeituraDTO;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorDTO {
	private String id;
	private String descricao;
	private TipoSensor tipo;
	private List<LeituraDTO> leituras;

	// Hidrometro
	@JsonIgnore
	private Long total_metros_cubicos, producaoLitrosAlvoMes;
	@JsonIgnore
	private Double vazaoSistema, percentualMinimoVazao;

	// Sensor Nivel
	@JsonIgnore
	private Double alturaTotalReservatorioCm, alturaSensorCm, medida1, medida2, capacidade;

	public SensorDTO(Sensor sensor, String periodo, boolean isLeitura) {
		this.id = sensor.getId();
		this.descricao = sensor.getDescricao();
		this.tipo = sensor.getTipoSensor();

		if (isLeitura) {
			try {

				if (periodo == null || periodo.isEmpty()) {
					this.leituras = sensor.getLeituras().stream().map(leitura -> new LeituraDTO(leitura, sensor))
							.toList();
				} else {
					ChronoUnit periodoUnidade;
					switch (periodo) {
					case "min": {
						periodoUnidade = ChronoUnit.MINUTES;
						break;
					}
					case "hora": {
						periodoUnidade = ChronoUnit.HOURS;
						break;
					}
					case "dia": {
						periodoUnidade = ChronoUnit.DAYS;
						break;
					}
					default:

						throw new IllegalArgumentException("Unexpected value: " + periodo);
					}

					Map<Instant, Double> agrupado = sensor.getLeituras().stream()
							.map(leitura -> new LeituraDTO(leitura, sensor))
							.collect(Collectors.groupingBy(leitura -> leitura.getMoment().truncatedTo(periodoUnidade),
									Collectors.summingDouble(LeituraDTO::getValor)));

					this.leituras = agrupado.entrySet().stream().map(entry -> {
						LeituraDTO dto = new LeituraDTO();
						dto.setMoment(entry.getKey());
						dto.setValor(entry.getValue());
						return dto;
					}).sorted((a, b) -> a.getMoment().compareTo(b.getMoment())).toList();
				}
			} catch (Exception e) {
				throw new DatabaseException(e.getMessage());
			}
		}

		definirEspecificos(sensor);

	}

	public SensorDTO(Sensor sensor) {
		this.id = sensor.getId();
		this.descricao = sensor.getDescricao();
		this.tipo = sensor.getTipoSensor();
//		this.leituras = equipamento.getLeituras().stream().map(l -> {
//			return new LeituraDTO(l);
//		}).toList();
		definirEspecificos(sensor);

	}

	private void definirEspecificos(Sensor sensor) {
		try {

			switch (this.tipo) {
			case Hidrometro: {
				this.total_metros_cubicos = (((Hidrometro) sensor).getQntTotalLitros() / 1000);
				this.vazaoSistema = ((Hidrometro) sensor).getVazaoSistema();
				this.producaoLitrosAlvoMes = ((Hidrometro) sensor).getProducaoLitrosAlvoMes();
				this.percentualMinimoVazao = ((Hidrometro) sensor).getPercentualMinimoVazao();
				break;
			}

			case SensorNivel: {
				this.alturaTotalReservatorioCm = (((SensorNivel) sensor).getAlturaTotalReservatorioCm());
				this.alturaSensorCm = (((SensorNivel) sensor).getAlturaSensorCm());
				this.medida1 = (((SensorNivel) sensor).getMedida1());
				this.medida2 = (((SensorNivel) sensor).getMedida2());
				this.capacidade = (((SensorNivel) sensor).getCapacidade());
				break;
			}

			default:
				throw new IllegalArgumentException("Unexpected value: " + this.tipo);
			}

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Double getPercentualMinimoVazao() {
		return percentualMinimoVazao;
	}

	public void setPercentualMinimoVazao(Double percentualMinimoVazao) {
		this.percentualMinimoVazao = percentualMinimoVazao;
	}

	public Double getVazaoSistema() {
		return vazaoSistema;
	}

	public void setVazaoSistema(Double vazaoSistema) {
		this.vazaoSistema = vazaoSistema;
	}

	public Long getProducaoLitrosAlvoMes() {
		return producaoLitrosAlvoMes;
	}

	public void setProducaoLitrosAlvoMes(Long producaoLitrosAlvoMes) {
		this.producaoLitrosAlvoMes = producaoLitrosAlvoMes;
	}

	public Long getTotal_metros_cubicos() {
		return total_metros_cubicos;
	}

	public void setTotal_metros_cubicos(Long total_metros_cubicos) {
		this.total_metros_cubicos = total_metros_cubicos;
	}

	public TipoSensor getTipo() {
		return tipo;
	}

	public void setTipo(TipoSensor tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getAlturaTotalReservatorioCm() {
		return alturaTotalReservatorioCm;
	}

	public void setAlturaTotalReservatorioCm(Double alturaTotalReservatorioCm) {
		this.alturaTotalReservatorioCm = alturaTotalReservatorioCm;
	}

	public Double getAlturaSensorCm() {
		return alturaSensorCm;
	}

	public void setAlturaSensorCm(Double alturaSensorCm) {
		this.alturaSensorCm = alturaSensorCm;
	}

	public Double getMedida1() {
		return medida1;
	}

	public void setMedida1(Double medida1) {
		this.medida1 = medida1;
	}

	public Double getMedida2() {
		return medida2;
	}

	public void setMedida2(Double medida2) {
		this.medida2 = medida2;
	}

	public Double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Double capacidade) {
		this.capacidade = capacidade;
	}

	public List<LeituraDTO> getLeituras() {
		return leituras;
	}

	public void setLeituras(List<LeituraDTO> leituras) {
		this.leituras = leituras;
	}

}
