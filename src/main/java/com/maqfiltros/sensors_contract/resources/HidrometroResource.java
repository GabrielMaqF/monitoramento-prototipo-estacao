package com.maqfiltros.sensors_contract.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.resources.generic.EquipamentoResourceGeneric;

@RestController
@RequestMapping(value = "/hidrometros")
public class HidrometroResource extends EquipamentoResourceGeneric<Hidrometro> {

    @Override
    protected TipoEquipamento getTipo() {
        return TipoEquipamento.Hidrometro;
    }
}

/*
package com.maqfiltros.sensors_contract.resources;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maqfiltros.sensors_contract.entities.Cliente;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.services.ClienteService;
import com.maqfiltros.sensors_contract.services.HidrometroService;

@RestController
@RequestMapping(value = "/hidrometros")
public class HidrometroResource {

	@Autowired
	private HidrometroService service;

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Hidrometro>> findAll(){
		List<Hidrometro> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Hidrometro> findById(@PathVariable Long id){
		Hidrometro obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/{id_cliente}")
	public ResponseEntity<Hidrometro> insert (@PathVariable Long id_cliente, @RequestBody Hidrometro obj){
		Cliente cliente = clienteService.findById(id_cliente);
		
		obj.setCliente(cliente);
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Hidrometro> update(@PathVariable Long id, @RequestBody Hidrometro obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
*/