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
			JOIN sensor e ON l.sensor_id = e.id
			JOIN hidrometro h ON h.id = e.id
			WHERE l.moment >= now() - CAST(:intervalo AS interval)
			  AND l.sensor_id = :idSensor
			GROUP BY minuto, h.pulsos_por_litro
			ORDER BY minuto
			""", nativeQuery = true)
	List<LeituraPorMinutoDTO> buscarLeiturasHidrometroPorIntervalo(@Param("idSensor") Long idSensor,
			@Param("intervalo") String intervalo);

	@Query(value = """
			SELECT
			    date_trunc('minute', l.moment) AS minuto,
			    (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
			FROM leitura l
			JOIN sensor e ON l.sensor_id = e.id
			JOIN hidrometro h ON h.id = e.id
			WHERE l.sensor_id = :idSensor
			GROUP BY minuto, h.pulsos_por_litro
			ORDER BY minuto
			""", nativeQuery = true)
	List<LeituraPorMinutoDTO> buscarLeiturasTratadas(@Param("idSensor") Long idSensor);
	
	@Query(value = """
		    SELECT
		        date_trunc('minute', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN sensor e ON l.sensor_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.sensor_id = :idSensor
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasPorDia(
		    @Param("idSensor") Long idSensor,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);
	@Query(value = """
		    SELECT
		        date_trunc('day', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN sensor e ON l.sensor_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.sensor_id = :idSensor
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasAgrupadasPorDia(
		    @Param("idSensor") Long idSensor,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);

	@Query(value = """
		    SELECT
		        date_trunc('minute', l.moment) AS minuto,
		        (SUM(l.valor::numeric) * h.pulsos_por_litro / 1000)::double precision AS valor
		    FROM leitura l
		    JOIN sensor e ON l.sensor_id = e.id
		    JOIN hidrometro h ON h.id = e.id
		    WHERE l.sensor = :idSensor
		      AND l.moment >= :inicio
		      AND l.moment < :fim
		    GROUP BY minuto, h.pulsos_por_litro
		    ORDER BY minuto
		    """, nativeQuery = true)
		List<LeituraPorMinutoDTO> buscarLeiturasAgrupadasPorMinuto(
		    @Param("idSensor") Long idSensor,
		    @Param("inicio") Instant inicio,
		    @Param("fim") Instant fim
		);

}