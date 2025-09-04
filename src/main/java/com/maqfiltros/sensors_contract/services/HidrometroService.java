package com.maqfiltros.sensors_contract.services;

import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.repositorys.HidrometroRepository;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceGeneric;

//@Service
@Service("hidrometroService")
public class HidrometroService extends SensorServiceGeneric<Hidrometro, HidrometroRepository> {

	@Override
	public Hidrometro updateLeitura(String id, Hidrometro obj) {
		Hidrometro entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		entity.setQntTotalLitros(obj.getQntProduzida());
		return repository.save(entity);
	}

	@Override
	protected void updateData(Hidrometro entity, Hidrometro obj) {
		entity.setQntTotalLitros(obj.getQntTotalLitros());
		entity.setPulsosPorLitro(obj.getPulsosPorLitro());
//		entity.setModelo(obj.getModelo());
		entity.setDescricao(obj.getDescricao());
	}

	@Override
	public TipoSensor getTipoSensor() {
		return TipoSensor.Hidrometro;
	}
}