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

import com.maqfiltros.sensors_contract.entities.Cliente;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.services.ClienteService;
import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceFactory;
import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceGeneric;

public abstract class EquipamentoResourceGeneric<T extends Equipamento> {

//    @Autowired
//    protected EquipamentoServiceGeneric<T, ?> service; // `?` para aceitar qualquer repositório específico

    @Autowired
    private EquipamentoServiceFactory serviceFactory;
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        List<T> list = getService().findAll();
        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<T>> findByClienteId(@PathVariable Long clienteId) {
        List<T> equipamentos = getService().findByClienteId(clienteId);
        return ResponseEntity.ok().body(equipamentos);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        T obj = getService().findById(id);
        return ResponseEntity.ok().body(obj);
    }
    
/*
    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        List<T> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<T>> findByClienteId(@PathVariable Long clienteId) {
        List<T> equipamentos = service.findByClienteId(clienteId);
        return ResponseEntity.ok().body(equipamentos);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        T obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
*/
//    @PostMapping(value = "/{id_cliente}")
//    public ResponseEntity<T> insert(@PathVariable Long id_cliente, @RequestBody T obj) {
//        Cliente cliente = clienteService.findById(id_cliente);
//        obj.setCliente(cliente);
//        obj = service.insert(obj);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(obj.getId()).toUri();
//        return ResponseEntity.created(uri).body(obj);
//    }
    
    @PostMapping(value = "/{id_cliente}")
    public ResponseEntity<T> insert(@PathVariable Long id_cliente, @RequestBody T obj) {
        TipoEquipamento tipo = obj.getTipoEquipamento();
        @SuppressWarnings("unchecked")
        EquipamentoServiceGeneric<T, ?> service = (EquipamentoServiceGeneric<T, ?>) serviceFactory.getService(tipo);
        
        Cliente cliente = clienteService.findById(id_cliente);
        obj.setCliente(cliente);
        
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//    	service.delete(id);
//    	return ResponseEntity.noContent().build();
//    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T obj) {
//        obj = service.update(id, obj);
//        return ResponseEntity.ok().body(obj);
//    }
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
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.services.ClienteService;
import com.maqfiltros.sensors_contract.services.EquipamentoService;

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResource {

	@Autowired
	private EquipamentoService service;

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Equipamento>> findAll(){
		List<Equipamento> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Equipamento> findById(@PathVariable Long id){
		Equipamento obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/{id_cliente}")
	public ResponseEntity<Equipamento> insert (@PathVariable Long id_cliente, @RequestBody Equipamento obj){
		
		switch (obj.getTipo()) {
			case Hidrometro: 
				obj = ((Hidrometro) obj);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + obj.getTipo());
		}

		Cliente cliente = clienteService.findById(id_cliente);
		obj.setCliente(cliente);
		
		System.out.println("TYPEOF: " + (obj.getClass()));
		
		if(obj instanceof Hidrometro) {
			
		}
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
	public ResponseEntity<Equipamento> update(@PathVariable Long id, @RequestBody Equipamento obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
*/
