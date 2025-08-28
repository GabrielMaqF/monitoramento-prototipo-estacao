package com.maqfiltros.sensors_contract.repositorys;

import org.springframework.stereotype.Repository;

import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.repositorys.generic.EquipamentoRepositoryGeneric;

@Repository
public interface SensorNivelRepository extends EquipamentoRepositoryGeneric<SensorNivel> {
}
