package com.maqfiltros.sensors_contract.repositorys;

import org.springframework.stereotype.Repository;

import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.repositorys.generic.EquipamentoRepositoryGeneric;

@Repository
public interface HidrometroRepository extends EquipamentoRepositoryGeneric<Hidrometro> {
}

/*
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.stereotype.Repository; import
 * com.maqfiltros.sensors_contract.entities.Hidrometro;
 * 
 * @Repository public interface HidrometroRepository extends
 * JpaRepository<Hidrometro, Long> { }
 */