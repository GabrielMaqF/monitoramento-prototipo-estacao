package com.maqfiltros.sensors_contract.services.generic;

import java.util.List;
//import java.util.Optional;

//import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.interfaces.consultas.SensorResumido;
import com.maqfiltros.sensors_contract.repositorys.generic.SensorRepositoryGeneric;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.exceptions.ResourceNotFoundException;

@Service
public abstract class SensorServiceGeneric<T extends Sensor, R extends SensorRepositoryGeneric<T>> {

	@Autowired
	protected R repository;

	public List<T> findAll() {
		return repository.findAll();
	}

	public List<T> findByEquipamentoId(Long equipamentoId) {
		return repository.findByEquipamentoId(equipamentoId);
	}

	public T findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado com ID: " + id));
	}

	public T insert(T obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public List<T> findAllSemLeituras() {
		return repository.findAllSemLeituras();
	}

	public List<SensorResumido> findSensorResumido() {
		return repository.findResumoSensores();
	}

	public T update(Long id, T obj) {
		T entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		updateData(entity, obj);
		return repository.save(entity);
	}

	// Método abstrato para permitir que cada subclasse implemente sua lógica de
	// atualização de leitura
	public abstract TipoSensor getTipoSensor();

	public abstract T updateLeitura(Long id, T obj);

	protected abstract void updateData(T entity, T obj);
}

/*
 * import java.util.List; import java.util.NoSuchElementException; import
 * java.util.Optional;
 * 
 * import org.hibernate.Hibernate; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.dao.DataIntegrityViolationException; import
 * org.springframework.dao.EmptyResultDataAccessException; import
 * org.springframework.stereotype.Service;
 * 
 * import com.maqfiltros.sensors_contract.entities.Equipamento; import
 * com.maqfiltros.sensors_contract.entities.Hidrometro; import
 * com.maqfiltros.sensors_contract.repositorys.EquipamentoRepository; import
 * com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
 * import com.maqfiltros.sensors_contract.services.exceptions.
 * ResourceNotFoundException;
 * 
 * @Service public class EquipamentoService {
 * 
 * @Autowired private EquipamentoRepository repository;
 * 
 * public List<Equipamento> findAll(){ return repository.findAll(); }
 * 
 * public Equipamento findById(Long id) { if (!(id instanceof Long)) { throw new
 * ResourceNotFoundException("ID fornecido não é do tipo Long."); }
 * 
 * try { Optional<Equipamento> obj = repository.findById(id);
 * System.out.println(obj.getClass()); return obj.get(); }catch
 * (NoSuchElementException e) { throw new ResourceNotFoundException(id); }catch
 * (Exception e) { e.printStackTrace(); throw new
 * DatabaseException(e.getMessage()); } }
 * 
 * public Equipamento insert(Equipamento obj) { return repository.save(obj); }
 * 
 * public void delete (Long id) { try { repository.deleteById(id); } catch
 * (EmptyResultDataAccessException e) { throw new ResourceNotFoundException(id);
 * } catch (DataIntegrityViolationException e) { throw new
 * DatabaseException(e.getMessage()); } catch (RuntimeException e) {
 * e.printStackTrace(); } }
 * 
 * public Equipamento update(Long id, Equipamento obj) { Equipamento entity =
 * repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
 * try { updateData(entity, obj); } catch (Exception e) { e.printStackTrace();
 * throw new IllegalArgumentException("Unexpected value: " + obj); } return
 * repository.save(entity); }
 * 
 * private void updateData(Equipamento entity, Equipamento obj) {
 * Hibernate.initialize(entity);
 * 
 * if (entity instanceof Hidrometro) { ((Hidrometro)
 * entity).setQntTotalLitros(((Hidrometro) obj).getQntTotalLitros());
 * ((Hidrometro) entity).setPulsosPorLitro(((Hidrometro)
 * obj).getPulsosPorLitro()); } else { throw new
 * IllegalArgumentException("O equipamento não é do tipo Hidrometro."); }
 * 
 * entity.setModelo(((Hidrometro) obj).getModelo());
 * entity.setDescricao(((Hidrometro) obj).getDescricao()); }
 * 
 * public Equipamento updateLeitura(Long id, Equipamento obj) { Equipamento
 * entity = repository.findById(id).orElseThrow(() -> new
 * ResourceNotFoundException(id));
 * 
 * if (entity instanceof Hidrometro) { ((Hidrometro)
 * entity).setQntTotalLitros(((Hidrometro) obj).getQntTotalLitros()); }
 * 
 * return repository.save(entity); }
 * 
 * }
 */