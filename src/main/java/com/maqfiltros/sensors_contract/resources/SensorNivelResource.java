package com.maqfiltros.sensors_contract.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.resources.generic.EquipamentoResourceGeneric;

@RestController
@RequestMapping(value = "/sensor-nivel")
public class SensorNivelResource extends EquipamentoResourceGeneric<SensorNivel> {
	@Override
	protected TipoEquipamento getTipo() {
		return TipoEquipamento.SensorNivel;
	}
}
