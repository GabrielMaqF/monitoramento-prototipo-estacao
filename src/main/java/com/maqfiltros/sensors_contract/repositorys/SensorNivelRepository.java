package com.maqfiltros.sensors_contract.repositorys;

import org.springframework.stereotype.Repository;

import com.maqfiltros.sensors_contract.entities.SensorNivel;
import com.maqfiltros.sensors_contract.repositorys.generic.SensorRepositoryGeneric;

@Repository
public interface SensorNivelRepository extends SensorRepositoryGeneric<SensorNivel> {
}
