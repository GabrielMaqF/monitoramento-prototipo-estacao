package com.maqfiltros.sensors_contract.repositorys;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maqfiltros.sensors_contract.dto.leitura.LeituraPorMinutoDTO;
import com.maqfiltros.sensors_contract.entities.Leitura;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
	@Query(value = """
			SELECT
			    date_trunc('minute', l.moment) AS minuto,
			    (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
			FROM leitura l
			JOIN equipamento e ON l.equipamento_id = e.id
			JOIN hidrometro h ON h.id = e.id
			WHERE l.moment >= now() - CAST(:intervalo AS interval)
			  AND l.equipamento_id = :idEquipamento
			GROUP BY minuto, h.pulsos_por_litro
			ORDER BY minuto
			""", nativeQuery = true)
	List<LeituraPorMinutoDTO> buscarLeiturasHidrometroPorIntervalo(@Param("idEquipamento") Long idEquipamento,
			@Param("intervalo") String intervalo);

	@Query(value = """
			SELECT
			    date_trunc('minute', l.moment) AS minuto,
			    (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
			FROM leitura l
			JOIN equipamento e ON l.equipamento_id = e.id
			JOIN hidrometro h ON h.id = e.id
			WHERE l.equipamento_id = :idEquipamento
			GROUP BY minuto, h.pulsos_por_litro
			ORDER BY minuto
			""", nativeQuery = true)
	List<LeituraPorMinutoDTO> buscarLeiturasTratadas(@Param("idEquipamento") Long idEquipamento);
	
	@Query(value = """
		    SELECT
		        date_trunc('minute', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN equipamento e ON l.equipamento_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.equipamento_id = :idEquipamento
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasPorDia(
		    @Param("idEquipamento") Long idEquipamento,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);
	@Query(value = """
		    SELECT
		        date_trunc('day', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN equipamento e ON l.equipamento_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.equipamento_id = :idEquipamento
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasAgrupadasPorDia(
		    @Param("idEquipamento") Long idEquipamento,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);

	@Query(value = """
		    SELECT
		        date_trunc('minute', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN equipamento e ON l.equipamento_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.equipamento_id = :idEquipamento
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasAgrupadasPorMinuto(
		    @Param("idEquipamento") Long idEquipamento,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);

}