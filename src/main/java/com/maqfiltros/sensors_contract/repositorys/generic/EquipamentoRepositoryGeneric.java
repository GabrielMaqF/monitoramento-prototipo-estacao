package com.maqfiltros.sensors_contract.repositorys.generic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.interfaces.consultas.EquipamentoResumido;

public interface EquipamentoRepositoryGeneric<T extends Equipamento> extends JpaRepository<T, Long> {
	List<T> findByEscolaId(Long clienteId);

	@Query("SELECT e FROM Equipamento e")
	List<T> findAllSemLeituras();

	/*
	 * @Query(""" SELECT e.id AS id, e.cliente.id AS clienteId, TYPE(e) AS tipo FROM
	 * Equipamento e """) List<EquipamentoResumido> findResumoEquipamentos();
	 */
	@Query(value = "SELECT e.id, e.escola_id AS escolaId, e.tipo_equipamento AS tipo FROM equipamento e", nativeQuery = true)
	List<EquipamentoResumido> findResumoEquipamentos();

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