package com.maqfiltros.sensors_contract.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maqfiltros.sensors_contract.adapter.entities.RelacaoDadosSwimp;
import java.util.List;

public interface RelacaoDadosSwimpRepository extends JpaRepository<RelacaoDadosSwimp, Long> {
	public List<RelacaoDadosSwimp> findByIdEscola(Long idEscola);

	public List<RelacaoDadosSwimp> findByIdEquipamento(Long idEquipamento);
}
