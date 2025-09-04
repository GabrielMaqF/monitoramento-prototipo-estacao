package com.maqfiltros.sensors_contract.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.services.EquipamentoService;

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResource {

	@Autowired
	private EquipamentoService service;

	@GetMapping
	public ResponseEntity<List<Equipamento>> findAll() {
		List<Equipamento> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Equipamento> findById(@PathVariable Long id) {
		Equipamento obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/{id_escola}")
	public ResponseEntity<Equipamento> insert(@PathVariable Long id_escola, @RequestBody Equipamento obj) {
		obj = service.insert(id_escola, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Void> delete(@PathVariable Long id) {
//		service.delete(id);
//		return ResponseEntity.noContent().build();
//	}
//
//	@PutMapping(value = "/{id}")
//	public ResponseEntity<Equipamento> update(@PathVariable Long id, @RequestBody Equipamento obj) {
//		obj = service.update(id, obj);
//		return ResponseEntity.ok().body(obj);
//	}
}
