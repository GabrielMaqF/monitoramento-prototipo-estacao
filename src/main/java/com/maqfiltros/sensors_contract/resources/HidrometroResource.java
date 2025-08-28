package com.maqfiltros.sensors_contract.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.resources.generic.EquipamentoResourceGeneric;

@RestController
@RequestMapping(value = "/hidrometros")
public class HidrometroResource extends EquipamentoResourceGeneric<Hidrometro> {

	@Override
	protected TipoEquipamento getTipo() {
		return TipoEquipamento.Hidrometro;
	}
}
