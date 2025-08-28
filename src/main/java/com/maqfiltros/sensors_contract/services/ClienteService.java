package com.maqfiltros.sensors_contract.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.dto.ClienteDTO;
import com.maqfiltros.sensors_contract.entities.Cliente;
import com.maqfiltros.sensors_contract.repositorys.ClienteRepository;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public List<ClienteDTO> findAllDTO() {
		List<Cliente> list = repository.findAll();
		return list.stream().map(cliente -> new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco()))
				.toList();
	}

	public Cliente findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.get();
	}

	public Cliente insert(Cliente obj) {
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

	public Cliente update(Long id, Cliente obj) {
		Cliente entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Cliente entity, Cliente obj) {
		entity.setNome(obj.getNome());
		entity.setEndereco(obj.getEndereco());
	}
}
