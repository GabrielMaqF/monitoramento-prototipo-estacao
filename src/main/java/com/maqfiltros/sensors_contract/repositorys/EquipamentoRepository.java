package com.maqfiltros.sensors_contract.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.interfaces.consultas.EquipamentoResumido;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
	List<Equipamento> findByEscolaId(Long escolaId);

	@Query("SELECT e FROM Equipamento e LEFT JOIN FETCH e.leituras WHERE e.id = :id")
	Optional<Equipamento> findByIdComLeituras(@Param("id") Long id);

	@Query(value = "SELECT e.id, e.escola_id AS escolaId, e.tipo_equipamento AS tipo FROM equipamento e", nativeQuery = true)
	List<EquipamentoResumido> findResumoEquipamentos();
}
