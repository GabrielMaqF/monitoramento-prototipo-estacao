package com.maqfiltros.sensors_contract.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.interfaces.consultas.SensorResumido;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
	List<Sensor> findByEquipamentoId(String equipamentoId);

	@Query("SELECT s FROM Sensor s LEFT JOIN FETCH s.leituras WHERE s.id = :id")
	Optional<Sensor> findByIdComLeituras(@Param("id") String id);

	@Query(value = "SELECT s.id, s.equipamento_id AS equipamentoId, s.tipo_sensor AS tipo FROM sensor s", nativeQuery = true)
	List<SensorResumido> findResumoSensores();
}
