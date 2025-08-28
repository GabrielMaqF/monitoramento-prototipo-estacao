package com.maqfiltros.sensors_contract.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.dto.EscolaDTO;
import com.maqfiltros.sensors_contract.entities.Escola;
import com.maqfiltros.sensors_contract.repositorys.EscolaRepository;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;

@Service
public class EscolaService {

	@Autowired
	private EscolaRepository repository;

	public List<Escola> findAll() {
		return repository.findAll();
	}

	public List<EscolaDTO> findAllDTO() {
		List<Escola> list = repository.findAll();
		return list.stream().map(cliente -> new EscolaDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco()))
				.toList();
	}

	public Escola findById(Long id) {
		Optional<Escola> obj = repository.findById(id);
		return obj.get();
	}

	public Escola insert(Escola obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public Escola update(Long id, Escola obj) {
		Escola entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Escola entity, Escola obj) {
		entity.setNome(obj.getNome());
		entity.setEndereco(obj.getEndereco());
	}
}
