package com.maqfiltros.sensors_contract.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.dto.SensorDTO;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.services.SensorService;

@RestController
@RequestMapping("/sensores")
public class SensorResource {

	private final SensorService sensorService;

	public SensorResource(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	@GetMapping("/escola/com-leituras/{escolaId}")
	public ResponseEntity<?> listarPorEscolaComOpcionalELeituras(@PathVariable String escolaId,
			@RequestParam(value = "tm", required = false) String tm) {
		List<Sensor> sensores = sensorService.buscarPorEscola(escolaId);
		List<SensorDTO> resultado = sensores.stream().map(e -> new SensorDTO(e, tm, true)).toList();
		return ResponseEntity.ok(resultado);
	}

	@GetMapping("/escola/{escolaId}")
	public ResponseEntity<?> listarPorEscola(@PathVariable String escolaId) {
		List<Sensor> sensores = sensorService.buscarPorEscola(escolaId);
		List<SensorDTO> resultado = sensores.stream().map(e -> new SensorDTO(e)).toList();
		return ResponseEntity.ok(resultado);
	}
}
