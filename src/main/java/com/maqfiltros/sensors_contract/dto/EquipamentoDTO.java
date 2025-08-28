package com.maqfiltros.sensors_contract.dto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.maqfiltros.sensors_contract.dto.leitura.LeituraDTO;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipamentoDTO {
	private Long id;
	private String descricao;
	private TipoEquipamento tipo;
	private List<LeituraDTO> leituras;

	// Hidrometro
	@JsonIgnore
	private Long total_metros_cubicos, producaoLitrosAlvoMes;
	@JsonIgnore
	private Double vazaoSistema, percentualMinimoVazao;

	// Sensor Nivel
	@JsonIgnore
	private Double alturaTotalReservatorioCm, alturaEquipamentoCm, medida1, medida2, capacidade;

	public EquipamentoDTO(Equipamento equipamento, String periodo, boolean isLeitura) {
		this.id = equipamento.getId();
		this.descricao = equipamento.getDescricao();
		this.tipo = equipamento.getTipoEquipamento();

		if (isLeitura) {
			try {

				if (periodo == null || periodo.isEmpty()) {
					this.leituras = equipamento.getLeituras().stream()
							.map(leitura -> new LeituraDTO(leitura, equipamento)).toList();
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

					Map<Instant, Double> agrupado = equipamento.getLeituras().stream()
							.map(leitura -> new LeituraDTO(leitura, equipamento))
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

		definirEspecificos(equipamento);

	}

	public EquipamentoDTO(Equipamento equipamento) {
		this.id = equipamento.getId();
		this.descricao = equipamento.getDescricao();
		this.tipo = equipamento.getTipoEquipamento();
//		this.leituras = equipamento.getLeituras().stream().map(l -> {
//			return new LeituraDTO(l);
//		}).toList();
		definirEspecificos(equipamento);

	}

	private void definirEspecificos(Equipamento equipamento) {
		try {

			switch (this.tipo) {
			case Hidrometro: {
				this.total_metros_cubicos = (((Hidrometro) equipamento).getQntTotalLitros() / 1000);
				this.vazaoSistema = ((Hidrometro) equipamento).getVazaoSistema();
				this.producaoLitrosAlvoMes = ((Hidrometro) equipamento).getProducaoLitrosAlvoMes();
				this.percentualMinimoVazao = ((Hidrometro) equipamento).getPercentualMinimoVazao();
				break;
			}

			case SensorNivel: {
				this.alturaTotalReservatorioCm = (((SensorNivel) equipamento).getAlturaTotalReservatorioCm());
				this.alturaEquipamentoCm = (((SensorNivel) equipamento).getAlturaEquipamentoCm());
				this.medida1 = (((SensorNivel) equipamento).getMedida1());
				this.medida2 = (((SensorNivel) equipamento).getMedida2());
				this.capacidade = (((SensorNivel) equipamento).getCapacidade());
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

	public TipoEquipamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Double getAlturaEquipamentoCm() {
		return alturaEquipamentoCm;
	}

	public void setAlturaEquipamentoCm(Double alturaEquipamentoCm) {
		this.alturaEquipamentoCm = alturaEquipamentoCm;
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
