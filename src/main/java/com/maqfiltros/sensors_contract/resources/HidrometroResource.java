package com.maqfiltros.sensors_contract.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.resources.generic.SensorResourceGeneric;

@RestController
@RequestMapping(value = "/hidrometros")
public class HidrometroResource extends SensorResourceGeneric<Hidrometro> {

	@Override
	protected TipoSensor getTipo() {
		return TipoSensor.Hidrometro;
	}
}
