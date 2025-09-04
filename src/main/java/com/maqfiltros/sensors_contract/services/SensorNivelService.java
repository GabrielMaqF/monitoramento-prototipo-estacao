package com.maqfiltros.sensors_contract.services;

import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.repositorys.SensorNivelRepository;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceGeneric;

//@Service
@Service("sensorNivelService")
public class SensorNivelService extends SensorServiceGeneric<SensorNivel, SensorNivelRepository> {

	@Override
	public SensorNivel updateLeitura(String id, SensorNivel obj) {
		SensorNivel entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
//		entity.setQntTotalLitros(obj.getQntProduzida());
		return repository.save(entity);
	}

	@Override
	protected void updateData(SensorNivel entity, SensorNivel obj) {
		// TODO Auto-generated method stub
//		entity.setQntTotalLitros(obj.getQntTotalLitros());
//		entity.setPulsosPorLitro(obj.getPulsosPorLitro());
//		entity.setModelo(obj.getModelo());
		entity.setDescricao(obj.getDescricao());
	}

	@Override
	public TipoSensor getTipoSensor() {
		return TipoSensor.SensorNivel;
	}
}