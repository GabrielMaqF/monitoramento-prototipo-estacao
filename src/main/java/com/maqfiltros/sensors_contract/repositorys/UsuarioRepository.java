package com.maqfiltros.sensors_contract.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maqfiltros.sensors_contract.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByUidFirebase(String uidFirebase);
}
