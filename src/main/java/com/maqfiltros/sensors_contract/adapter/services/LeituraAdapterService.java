package com.maqfiltros.sensors_contract.adapter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.adapter.dto.LeituraSwimpDTO;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.services.LeituraService;
import com.maqfiltros.sensors_contract.services.SensorService;

@Service
public class LeituraAdapterService {

	@Autowired
	private LeituraService service;

	@Autowired
	private SensorService sensorService;

	public ResponseEntity<Void> inserirLeiturasSwimp(LeituraSwimpDTO dto) {
		Leitura l = new Leitura();
		
		String idSensor = dto.getDeviceId() + "_" + dto.getRecursoMonitoradoId();
		sensorService.findById(idSensor);
		

		return null;
	}

}
