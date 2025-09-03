package com.maqfiltros.sensors_contract.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.interfaces.consultas.SensorResumido;
import com.maqfiltros.sensors_contract.repositorys.SensorRepository;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;

@Service
public class SensorService {

	private final SensorRepository sensorRepository;

	public SensorService(SensorRepository sensorRepository) {
		this.sensorRepository = sensorRepository;
	}

	public List<Sensor> buscarPorEscola(Long escolaId) {
		return sensorRepository.findByEscolaId(escolaId);
	}

	public Sensor findByIdComLeituras(Long id) {
		return sensorRepository.findByIdComLeituras(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado"));
	}

	public Sensor findById(Long id) {
		return sensorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com ID: " + id));
	}

	public List<Sensor> findAll() {
		return sensorRepository.findAll();
	}

	public List<SensorResumido> findSensorResumido() {
		return sensorRepository.findResumoSensores();
	}
}
