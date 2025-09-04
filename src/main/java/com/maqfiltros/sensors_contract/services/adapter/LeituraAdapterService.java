package com.maqfiltros.sensors_contract.services.adapter;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.dto.leitura.LeituraSwimpDTO;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.services.SensorService;

@Service
public class LeituraAdapterService {

	@Autowired
	private SensorService sensorService;

//	public Leitura adaptar(LeituraSwimpDTO dtoExterno) {
//		// 1. Busca o Sensor no seu banco de dados usando o ID fornecido.
//		Sensor sensor = sensorService.findById( dto);
//
//		// 2. Converte o Unix Timestamp (segundos) para Instant (UTC).
//		Instant momento = Instant.ofEpochSecond(dtoExterno.getTimestampMedicao());
//
//		// 3. Converte a medição para String, como sua entidade Leitura espera.
//		String valor = String.valueOf(dtoExterno.getMedicaoCorrente());
//
//		// 4. Cria a nova entidade Leitura com os dados adaptados.
//		return new Leitura(null, momento, valor, sensor);
//	}
}