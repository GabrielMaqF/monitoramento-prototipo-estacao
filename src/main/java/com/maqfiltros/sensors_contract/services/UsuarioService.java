package com.maqfiltros.sensors_contract.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Usuario;
import com.maqfiltros.sensors_contract.repositorys.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repository;

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.get();
	}

	public Usuario findByUidFirebase(String uidFirebase) {
		return repository.findByUidFirebase(uidFirebase)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com uidFirebase: " + uidFirebase));

	}

	public Usuario insert(Usuario obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
//	public Usuario update(Long id, Usuario obj) {
//		Usuario entity = repository.getReferenceById(id);
//		updateData(entity, obj);
//		return repository.save(entity);
//	}
//	
//	private void updateData(Escola entity, Escola obj) {
//		entity.setNome(obj.getNome());
//		entity.setEndereco(obj.getEndereco());
//	}

}
