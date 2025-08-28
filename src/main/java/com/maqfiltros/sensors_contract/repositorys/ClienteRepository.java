package com.maqfiltros.sensors_contract.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maqfiltros.sensors_contract.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}