package com.maqfiltros.sensors_contract.repositorys.generic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.interfaces.consultas.SensorResumido;

public interface SensorRepositoryGeneric<T extends Sensor> extends JpaRepository<T, Long> {
	List<T> findByEquipamentoId(Long clienteId);

	@Query("SELECT s FROM Sensor s")
	List<T> findAllSemLeituras();

	/*
	 * @Query(""" SELECT e.id AS id, e.cliente.id AS clienteId, TYPE(e) AS tipo FROM
	 * Equipamento e """) List<EquipamentoResumido> findResumoEquipamentos();
	 */
	@Query(value = "SELECT s.id, s.equipamento_id AS equipamentoId, s.tipo_sensor AS tipo FROM sensor s", nativeQuery = true)
	List<SensorResumido> findResumoSensores();

}

/*
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.repository.NoRepositoryBean;
 * 
 * import com.maqfiltros.sensors_contract.entities.Equipamento;
 * 
 * @NoRepositoryBean public interface EquipamentoRepository extends
 * JpaRepository<Equipamento, Long>{
 * 
 * }
 */