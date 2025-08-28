package com.maqfiltros.sensors_contract.resources;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maqfiltros.sensors_contract.dto.leitura.LeituraPorMinutoDTO;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.EquipamentoService;
import com.maqfiltros.sensors_contract.services.LeituraService;

@RestController
@RequestMapping(value = "/leituras")
public class LeituraResource {

	@Autowired
	private LeituraService service;


    @Autowired
    private EquipamentoService equipamentoService;
    
//	@Autowired
//	private EquipamentoServiceFactory serviceFactory;

//	@Autowired
//	private EquipamentoServiceGeneric equipamentoService;

	@GetMapping
	public ResponseEntity<List<Leitura>> findAll() {
		List<Leitura> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Leitura> findById(@PathVariable Long id) {
		Leitura obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/agrupadas/{idEquipamento}/{periodo}/{unidadeTempo}")
	public List<LeituraPorMinutoDTO> getLeiturasAgrupadasPorMinuto(@PathVariable Long idEquipamento,
			@PathVariable Integer periodo, @PathVariable String unidadeTempo) {
		return service.obterLeiturasTratadasUltimoPeriodo(idEquipamento, (periodo + " " + unidadeTempo));
	}

	@GetMapping("/tratadas/{idEquipamento}")
	public List<LeituraPorMinutoDTO> getLeiturasTratadas(@PathVariable Long idEquipamento) {
		return service.obterLeiturasTratadas(idEquipamento);
	}

	@GetMapping("/dia/{idEquipamento}")
	public List<LeituraPorMinutoDTO> getLeiturasPorDia(@PathVariable Long idEquipamento,
			@RequestParam("data") String dataBR) {

		return service.obterLeiturasPorPeriodo(idEquipamento, dataBR);
	}

	@GetMapping("/periodo/{idEquipamento}")
	public List<LeituraPorMinutoDTO> getLeiturasPorPeriodo(@PathVariable Long idEquipamento,
			@RequestParam("dt_inicio") String dtInicio, @RequestParam("dt_fim") String dtFim) {

		return service.obterLeiturasPorPeriodo(idEquipamento, dtInicio, dtFim);
	}

	@GetMapping("/mes/{idEquipamento}")
	public List<LeituraPorMinutoDTO> getLeiturasMes(@PathVariable Long idEquipamento,
			@RequestParam(value = "tm", required = false, defaultValue = "min") String tm,
			@RequestParam(value = "mes", required = false) String mes) {
		return service.obterLeiturasPorMes(idEquipamento, tm, mes);
	}

	@PostMapping(value = "/{id}/{valor}")
	public ResponseEntity<Leitura> insert(@PathVariable Long id, @PathVariable String valor) {
		try {
//			@SuppressWarnings("unchecked")
//			EquipamentoServiceGeneric<Equipamento, ?> equipamentoService = (EquipamentoServiceGeneric<Equipamento, ?>) serviceFactory
//					.getService(TipoEquipamento.Hidrometro);

			Equipamento equipamento = equipamentoService.findById(id); // O findById é genérico, então funciona.

//			Equipamento equipamento = equipamentoService.findById(id);
			Leitura obj = new Leitura(null, Instant.now(), valor, equipamento);

			obj = service.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/" + obj.getId())
					.buildAndExpand(obj.getId()).toUri();

			return ResponseEntity.created(uri).body(obj);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
