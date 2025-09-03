package com.maqfiltros.sensors_contract.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.resources.generic.SensorResourceGeneric;

@RestController
@RequestMapping(value = "/sensor-nivel")
public class SensorNivelResource extends SensorResourceGeneric<SensorNivel> {
	@Override
	protected TipoSensor getTipo() {
		return TipoSensor.SensorNivel;
	}
}
