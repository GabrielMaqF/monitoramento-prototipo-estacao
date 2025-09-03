package com.maqfiltros.sensors_contract.resources.generic;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maqfiltros.sensors_contract.entities.Escola;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.enums.TipoSensor;
import com.maqfiltros.sensors_contract.services.EscolaService;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceFactory;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceGeneric;

public abstract class SensorResourceGeneric<T extends Sensor> {

	@Autowired
	private SensorServiceFactory serviceFactory;

	@Autowired
	private EscolaService escolaService;

	@GetMapping
	public ResponseEntity<List<T>> findAll() {
		List<T> list = getService().findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/escola/{escolaId}")
	public ResponseEntity<List<T>> findByEscolaId(@PathVariable Long escolaId) {
		List<T> equipamentos = getService().findByEscolaId(escolaId);
		return ResponseEntity.ok().body(equipamentos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<T> findById(@PathVariable Long id) {
		T obj = getService().findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/{id_escola}")
	public ResponseEntity<T> insert(@PathVariable Long id_escola, @RequestBody T obj) {
		TipoSensor tipo = obj.getTipoSensor();
		@SuppressWarnings("unchecked")
		SensorServiceGeneric<T, ?> service = (SensorServiceGeneric<T, ?>) serviceFactory.getService(tipo);

		Escola escola = escolaService.findById(id_escola);
		obj.setEscola(escola);

		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		getService().delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T obj) {
		// Primeiro, obtemos o tipo do equipamento a partir do objeto recebido
		TipoSensor tipo = obj.getTipoSensor();

		// Usamos o factory para obter o serviço correto
		@SuppressWarnings("unchecked")
		SensorServiceGeneric<T, ?> service = (SensorServiceGeneric<T, ?>) serviceFactory.getService(tipo);

		// Chamamos o método no serviço específico
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@SuppressWarnings("unchecked")
	private SensorServiceGeneric<T, ?> getService() {
		return (SensorServiceGeneric<T, ?>) serviceFactory.getService(getTipo());
	}

	protected abstract TipoSensor getTipo();
}
