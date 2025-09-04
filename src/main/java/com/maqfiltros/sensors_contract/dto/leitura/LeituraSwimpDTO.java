package com.maqfiltros.sensors_contract.dto.leitura;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LeituraSwimpDTO {

	@JsonProperty("device_id")
	private String deviceId;

	@JsonProperty("escola_id")
	private Long escolaId;

	@JsonProperty("recurso_monitorado_id")
	private Long recursoMonitoradoId; // Este será o ID do seu Sensor

	@JsonProperty("ts_med")
	private long timestampMedicao; // Recebido como Unix Timestamp (long)

	@JsonProperty("med_corrente")
	private double medicaoCorrente; // Recebido como número
}
