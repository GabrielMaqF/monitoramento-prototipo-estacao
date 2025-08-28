package com.maqfiltros.sensors_contract.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.interfaces.consultas.EquipamentoResumido;
import com.maqfiltros.sensors_contract.repositorys.EquipamentoRepository;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;

@Service
public class EquipamentoService {

	private final EquipamentoRepository equipamentoRepository;

	public EquipamentoService(EquipamentoRepository equipamentoRepository) {
		this.equipamentoRepository = equipamentoRepository;
	}

	public List<Equipamento> buscarPorCliente(Long clienteId) {
		return equipamentoRepository.findByClienteId(clienteId);
	}

	public Equipamento findByIdComLeituras(Long id) {
		return equipamentoRepository.findByIdComLeituras(id)
				.orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado"));
	}

	public Equipamento findById(Long id) {
		return equipamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado com ID: " + id));
	}

	public List<Equipamento> findAll() {
		return equipamentoRepository.findAll();
	}

	public List<EquipamentoResumido> findEquipamentoResumido() {
		return equipamentoRepository.findResumoEquipamentos();
	}
}
