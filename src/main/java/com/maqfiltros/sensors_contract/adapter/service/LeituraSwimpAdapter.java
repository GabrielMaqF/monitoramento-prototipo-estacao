package com.maqfiltros.sensors_contract.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maqfiltros.sensors_contract.adapter.LeituraAdapter;
import com.maqfiltros.sensors_contract.dto.leitura.LeituraSwimpDTO;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.services.LeituraService;
import com.maqfiltros.sensors_contract.services.SensorService;

@Component
public class LeituraSwimpAdapter implements LeituraAdapter<LeituraSwimpDTO> {
    
	@Autowired
	private LeituraService service;
	
	@Autowired
	private SensorService sensorService;

	@Override
	public Leitura adapter(LeituraSwimpDTO dto) {
		Leitura l = new Leitura();
		
		String idSensor = dto.getDeviceId() + "_" + dto.getRecursoMonitoradoId();
		sensorService.findById(idSensor);
		
		
		
		return null;
	}

}
