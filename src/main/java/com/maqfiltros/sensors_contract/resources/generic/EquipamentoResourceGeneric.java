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

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Escola;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.services.EscolaService;
import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceFactory;
import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceGeneric;

public abstract class EquipamentoResourceGeneric<T extends Equipamento> {

	@Autowired
	private EquipamentoServiceFactory serviceFactory;

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
		TipoEquipamento tipo = obj.getTipoEquipamento();
		@SuppressWarnings("unchecked")
		EquipamentoServiceGeneric<T, ?> service = (EquipamentoServiceGeneric<T, ?>) serviceFactory.getService(tipo);

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
		TipoEquipamento tipo = obj.getTipoEquipamento();

		// Usamos o factory para obter o serviço correto
		@SuppressWarnings("unchecked")
		EquipamentoServiceGeneric<T, ?> service = (EquipamentoServiceGeneric<T, ?>) serviceFactory.getService(tipo);

		// Chamamos o método no serviço específico
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@SuppressWarnings("unchecked")
	private EquipamentoServiceGeneric<T, ?> getService() {
		return (EquipamentoServiceGeneric<T, ?>) serviceFactory.getService(getTipo());
	}

	protected abstract TipoEquipamento getTipo();
}
