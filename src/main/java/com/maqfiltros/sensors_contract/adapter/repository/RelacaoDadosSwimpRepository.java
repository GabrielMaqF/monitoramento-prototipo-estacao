package com.maqfiltros.sensors_contract.adapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maqfiltros.sensors_contract.adapter.entities.RelacaoDadosSwimp;

public interface RelacaoDadosSwimpRepository extends JpaRepository<RelacaoDadosSwimp, Long> {
	public List<RelacaoDadosSwimp> findByIdEscola(Long idEscola);

	public RelacaoDadosSwimp findByIdSwimp(String idSwimp);

}
