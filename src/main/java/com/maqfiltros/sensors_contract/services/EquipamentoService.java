package com.maqfiltros.sensors_contract.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Escola;
import com.maqfiltros.sensors_contract.repositorys.EquipamentoRepository;

@Service
public class EquipamentoService {
	@Autowired
	private EquipamentoRepository repository;

	@Autowired
	private EscolaService escolaService;
	
	public List<Equipamento> findAll() {
		return repository.findAll();
	}

	public Equipamento findById(Long id) {
		Optional<Equipamento> obj = repository.findById(id);
		return obj.get();
	}

	public Equipamento insert(Long id_escola, Equipamento obj) {
		Escola escola = escolaService.findById(id_escola);
		obj.setEscola(escola);
		return repository.save(obj);
	}

}
