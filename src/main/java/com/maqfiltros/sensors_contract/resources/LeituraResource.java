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
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.LeituraService;
import com.maqfiltros.sensors_contract.services.SensorService;

@RestController
@RequestMapping(value = "/leituras")
public class LeituraResource {

	@Autowired
	private LeituraService service;

	@Autowired
	private SensorService sensorService;

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

	@GetMapping("/agrupadas/{idSensor}/{periodo}/{unidadeTempo}")
	public List<LeituraPorMinutoDTO> getLeiturasAgrupadasPorMinuto(@PathVariable Long idSensor,
			@PathVariable Integer periodo, @PathVariable String unidadeTempo) {
		return service.obterLeiturasTratadasUltimoPeriodo(idSensor, (periodo + " " + unidadeTempo));
	}

	@GetMapping("/tratadas/{idSensor}")
	public List<LeituraPorMinutoDTO> getLeiturasTratadas(@PathVariable Long idSensor) {
		return service.obterLeiturasTratadas(idSensor);
	}

	@GetMapping("/dia/{idSensor}")
	public List<LeituraPorMinutoDTO> getLeiturasPorDia(@PathVariable Long idSensor,
			@RequestParam("data") String dataBR) {

		return service.obterLeiturasPorPeriodo(idSensor, dataBR);
	}

	@GetMapping("/periodo/{idSensor}")
	public List<LeituraPorMinutoDTO> getLeiturasPorPeriodo(@PathVariable Long idSensor,
			@RequestParam("dt_inicio") String dtInicio, @RequestParam("dt_fim") String dtFim) {

		return service.obterLeiturasPorPeriodo(idSensor, dtInicio, dtFim);
	}

	@GetMapping("/mes/{idSensor}")
	public List<LeituraPorMinutoDTO> getLeiturasMes(@PathVariable Long idSensor,
			@RequestParam(value = "tm", required = false, defaultValue = "min") String tm,
			@RequestParam(value = "mes", required = false) String mes) {
		return service.obterLeiturasPorMes(idSensor, tm, mes);
	}

	@PostMapping(value = "/{id}/{valor}")
	public ResponseEntity<Leitura> insert(@PathVariable String id, @PathVariable String valor) {
		try {
//			@SuppressWarnings("unchecked")
//			EquipamentoServiceGeneric<Equipamento, ?> equipamentoService = (EquipamentoServiceGeneric<Equipamento, ?>) serviceFactory
//					.getService(TipoEquipamento.Hidrometro);

			Sensor sensor = sensorService.findById(id); // O findById é genérico, então funciona.

//			Equipamento equipamento = equipamentoService.findById(id);
			Leitura obj = new Leitura(null, Instant.now(), valor, sensor);

			obj = service.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/" + obj.getId())
					.buildAndExpand(obj.getId()).toUri();

			return ResponseEntity.created(uri).body(obj);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
